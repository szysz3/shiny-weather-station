# shiny-weather-station
## setup
Lorem ipsum.
### backend (on RaspberryPi)
1. make sure you have enabled SPI, I2C if not please do so with:
```
sudo raspi-config
```
2. make sure you have [docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/) installed if not please install [docker](https://www.docker.com/) with:
```
curl -sSL https://get.docker.com | sh
sudo usermod -aG docker $USER 
```
and [docker-compose](https://docs.docker.com/compose/) with 
````
sudo pip install docker-compose
````
  


### client (on Nexus 7 with removed SystemUI)
