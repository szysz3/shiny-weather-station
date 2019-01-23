# shiny-weather-station
## setup
Lorem ipsum.
### backend (on RaspberryPi)
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

### client (on Nexus 7 with removed SystemUI)
