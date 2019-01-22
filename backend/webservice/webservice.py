import psycopg2
import datetime
import Adafruit_BMP.BMP085 as BMP085
import Adafruit_DHT as DHT
import json
import os
from sds import read_pm
from flask import Flask, jsonify
from os import listdir
from os.path import isfile, join

DT_FORMAT = "%Y-%m-%d %H:%M:%S"
DB_CONNECTION_STRING = "host='db' dbname='shinyweather' user='pi' password='pipassword123'"
NUMBER_FORMAT = "{0:0.2f}"
PHOTO__PATH = "/var/www/html/photos/"

app = Flask(__name__)


@app.route('/weather_conditions', methods=['GET'])
def get_current_conditions():
    try:
        db_connection = psycopg2.connect(DB_CONNECTION_STRING)
    except:
        print datetime.datetime.now().strftime(DT_FORMAT) + \
              " [ERROR]: db connection problem"
        return
    else:
        cursor = db_connection.cursor()
        cursor.execute("""SELECT date, data FROM current_conditions ORDER BY date desc LIMIT 1""")

        current_conditions_rows = cursor.fetchall()
        cursor.execute("""SELECT date, data FROM forecast ORDER BY date desc LIMIT 1""")
        forecast_rows = cursor.fetchall()

        # bmp sensor
        bmp_sensor = BMP085.BMP085()

        # dht sensor
        dht_sensor = DHT.DHT22
        humidity, temperature = DHT.read_retry(dht_sensor, 17)
        pm25_internal, pm10_internal = read_pm()

    weather_data = json.loads(json.dumps(current_conditions_rows[0][1]))

    weather_data["pm25_internal"] = pm25_internal
    weather_data["pm10_internal"] = pm10_internal
    weather_data["temp"] = float(NUMBER_FORMAT.format(temperature))
    weather_data["humidity"] = float(NUMBER_FORMAT.format(humidity))
    weather_data["pressure"] = float(NUMBER_FORMAT.format(bmp_sensor.read_pressure() / 100))

    return jsonify({"forecast": forecast_rows[0][1],
                    "current_conditions": weather_data})

@app.route('/photos', methods=['GET'])
def get_photos():
    ipaddr = os.environ['HOST_IP_ADDR']
    onlyfiles = [f for f in listdir(PHOTO__PATH) if isfile(join(PHOTO__PATH, f))]
    response = []
    for file in onlyfiles:
        response.append("http://" +ipaddr + "/photos/" + file)

    return jsonify({"photos": response})
