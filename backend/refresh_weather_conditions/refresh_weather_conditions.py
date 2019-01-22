import httplib2
import urllib
import datetime
import psycopg2
import json
import os
from time import sleep

CITY_NAME = os.environ['CITY_NAME']
LAT = os.environ['LAT']
LON = os.environ['LON']
AIRLY_API_KEY = os.environ['AIRLY_API_KEY']
OWM_API_KEY = os.environ['OWM_API_KEY']
OWM_LANGUAGE = os.getenv('OWM_LANGUAGE', 'en')
OWM_UNITS = os.getenv('OWM_UNITS', 'metric')

DT_FORMAT = "%Y-%m-%d %H:%M:%S"
BASE_REQUEST_URL = "http://api.openweathermap.org/data/2.5/"
BASE_AIRLY_URL = "https://airapi.airly.eu/v1/nearestSensor/measurements"
AIRLY_REQUEST_PARAMS = {'latitude': LAT,
                        'longitude': LON,
                        'maxDistance': '1000'}
AIRLY_HEADER = {'apikey': AIRLY_API_KEY}
REQUEST_PARAMS = {'q': CITY_NAME,
                  'APPID': OWM_API_KEY,
                  'units': OWM_UNITS,
                  'lang': OWM_LANGUAGE}
DB_CONNECTION_STRING = "host='db' dbname='shinyweather' user='pi' password='pipassword123'"

MAIN_KEY = 'main'
TEMP_MAX_KEY = 'temp_max'
TEMP_MIN_KEY = 'temp_min'
LIST_KEY = 'list'
ICON_ID_KEY = 'icon_id'
ICON_KEY = 'icon'
WEATHER_KEY = 'weather'

AIR_QUALITY_KEY = 'airQualityIndex'
PM25_KEY = 'pm25'
PM10_KEY = 'pm10'
POLLUTION_KEY = 'pollutionLevel'


def perform_airly_request():

    parameters = urllib.urlencode(AIRLY_REQUEST_PARAMS)
    try:
        request_url = BASE_AIRLY_URL + "?" + parameters
        resp, content = httplib2.Http().request(request_url, "GET", "", AIRLY_HEADER)
    except httplib2.ServerNotFoundError:
        print datetime.datetime.now().strftime(DT_FORMAT) + \
              " [ERROR]: server not found, check internet connection."
    else:
        print request_url + " : " + resp['status']
        return content


def perform_weather_request(web_method_name):

    parameters = urllib.urlencode(REQUEST_PARAMS)
    try:
        request_url = BASE_REQUEST_URL + web_method_name + "?" + parameters
        resp, content = httplib2.Http().request(request_url, "GET")
    except httplib2.ServerNotFoundError:
        print datetime.datetime.now().strftime(DT_FORMAT) + \
              " [ERROR]: server not found, check internet connection."
    else:
        print request_url + " : " + resp['status']
        return content


def download_forecast_data():
    return perform_weather_request("forecast")


def download_current_conditions_data():
    return perform_weather_request("weather")


def parse_current_conditions_data(current_conditions_raw_data, airly_raw_data):
    current_conditions_full_data = json.loads(current_conditions_raw_data)
    airly_full_data = json.loads(airly_raw_data)

    parsed_current_conditions_data = {
        TEMP_MAX_KEY: current_conditions_full_data[MAIN_KEY][TEMP_MAX_KEY],
        TEMP_MIN_KEY: current_conditions_full_data[MAIN_KEY][TEMP_MIN_KEY],
        "temp": current_conditions_full_data[MAIN_KEY]["temp"],
        "humidity": current_conditions_full_data[MAIN_KEY]["humidity"],
        "pressure": current_conditions_full_data[MAIN_KEY]["pressure"],
        "description": current_conditions_full_data["weather"][0]["description"],
        "icon_id": current_conditions_full_data["weather"][0]["icon"],
        "name": CITY_NAME,
        "humidity_symbol": '%',
        "pressure_unit": 'hPA',
        AIR_QUALITY_KEY: airly_full_data[AIR_QUALITY_KEY],
        PM25_KEY: airly_full_data[PM25_KEY],
        PM10_KEY: airly_full_data[PM10_KEY],
        POLLUTION_KEY: airly_full_data[POLLUTION_KEY]
    }
    return json.dumps(parsed_current_conditions_data)


def parse_forecast_data(forecast_raw_data):
    forecast_full_data = json.loads(forecast_raw_data)

    hour_forecast = []
    day_forecast = {}
    for x in range(0, len(forecast_full_data[LIST_KEY])):
        forecast = forecast_full_data[LIST_KEY][x]

        day = forecast['dt_txt'].split(" ")[0]

        forecast_temp_max = forecast[MAIN_KEY][TEMP_MAX_KEY]
        forecast_temp_min = forecast[MAIN_KEY][TEMP_MIN_KEY]
        if day in day_forecast:
            if day_forecast[day][TEMP_MAX_KEY] < forecast_temp_max:
                day_forecast[day][TEMP_MAX_KEY] = forecast_temp_max
                day_forecast[day][ICON_ID_KEY] = forecast[WEATHER_KEY][0][ICON_KEY]

            if day_forecast[day][TEMP_MIN_KEY] > forecast_temp_min:
                day_forecast[day][TEMP_MIN_KEY] = forecast_temp_min
        else:
            day_forecast[day] = {TEMP_MAX_KEY: forecast_temp_max,
                                 TEMP_MIN_KEY: forecast_temp_min,
                                 "day": forecast['dt'],
                                 ICON_ID_KEY: forecast[WEATHER_KEY][0][ICON_KEY]}
        if x < 8:
            hour_forecast.append({'hour': forecast['dt'],
                                  'temp': forecast[MAIN_KEY]['temp'],
                                  ICON_ID_KEY: forecast[WEATHER_KEY][0][ICON_KEY]})

    return json.dumps({"hour_forecast": hour_forecast,
                       "day_forecast": sorted(day_forecast.values())})


def main():
    while True:
        try:
            db_connection = psycopg2.connect(DB_CONNECTION_STRING)
        except psycopg2.Error:
            print datetime.datetime.now().strftime(DT_FORMAT) + " [ERROR]: db connection problem."
        else:
            forecast_data = download_forecast_data()
            parsed_forecast_data = parse_forecast_data(forecast_data)
            forecast_query = 'INSERT INTO forecast (date, data) VALUES (\'{0}\',\'{1}\');'\
                .format(datetime.datetime.now(), parsed_forecast_data)

            cursor = db_connection.cursor()
            cursor.execute(forecast_query)
            db_connection.commit()

            current_conditions_data = download_current_conditions_data()
            airly_data = perform_airly_request()
            parsed_current_conditions_data = parse_current_conditions_data(current_conditions_data, airly_data)
            current_conditions_query = 'INSERT INTO current_conditions (date, data) VALUES (\'{0}\',\'{1}\');'\
                .format(datetime.datetime.now(), parsed_current_conditions_data)
            cursor.execute(current_conditions_query)
            db_connection.commit()

            db_connection.close()
        sleep(600)

if __name__ == "__main__":
    main()

