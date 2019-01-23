# shiny-weather-station

**shiny-weather-station** is a mix of weather station, alarm clock, clock and photo frame.

**shiny-weather-station** was developed and tested on Raspberry Pi 3 B (backend) and Nexus 7 (client) with Android 6.0.1 (AOSP build with removed SystemUI). 

Backend constists of 3 docker containers: 
* [db](https://github.com/szysz3/shiny-weather-station/tree/master/backend/db) (PostgreSQL), 

* [forecast](https://github.com/szysz3/shiny-weather-station/tree/master/backend/refresh_weather_conditions) ([Open Weather Map](https://openweathermap.org), [Airly](https://airly.eu/en/) and some python scripts for parsing), 

* [sensors, apache and REST API](https://github.com/szysz3/shiny-weather-station/tree/master/backend/webservice) ([Flask](http://flask.pocoo.org) and some python scripts for reading data from sensors)

### backend (Raspberry Pi 3 B)
![RPI](https://i.imgur.com/QElSfV2.jpg)

### client (Nexus 7 with Android 6.0.1)

Simple Android app with three modes:
* clock / alarm clock
* weather data
* photo frame

[youtube video](https://youtu.be/_AMYQHY-znQ)

## setup

### backend
1. make sure you have enabled SPI, I2C if not please do so with:
```
sudo raspi-config
```
2. make sure you have [docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/) installed. If not please install both with:
```
curl -sSL https://get.docker.com | sh
sudo usermod -aG docker $USER 
sudo pip install docker-compose
````
3. download sources with:
```
git clone -b release/1.0 https://github.com/szysz3/shiny-weather-station.git && chmod +x shiny-weather-station/backend/run.sh
```
4. provide valid API keys, latitude, longitude and city name:
```
nano shiny-weather-station/backend/refresh_weather_conditions/Dockerfile
```

> ENV AIRLY_API_KEY=4aa4fc5c25e7420fa0dce08cec2cb322

Get Airly API key [here](https://developer.airly.eu/register)

> ENV OWM_API_KEY=[PUT OPEN WEATHER MAPS API KEY HERE]

Get Open Weather Map API key [here](https://home.openweathermap.org/users/sign_up)

5. build and run containers with:
```
cd shiny-weather-station/backend && ./run.sh
```

6. after a while containers should be up and running:
```
web_1                 |  * Serving Flask app "webservice.py"
web_1                 |  * Environment: production
web_1                 |    WARNING: Do not use the development server in a production environment.
web_1                 |    Use a production WSGI server instead.
web_1                 |  * Debug mode: off
web_1                 |  * Running on http://0.0.0.0:5000/ (Press CTRL+C to quit)
```

### client
1. edit and replace `BASE_URL` with IP address of your RaspberryPi

> private const val BASE_URL = "[PUT YOUR RPI WEATHER STATION IP HERE]"

e.g `private const val BASE_URL = "http://192.168.137.148:5000"`
