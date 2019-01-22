#!/bin/bash
HOST_IP_ADDR=$(ip -4 addr show wlan0 | grep -Po "inet \K[\d.]+")
sed -i "s/%HOST_IP_ADDR%/$HOST_IP_ADDR/g" webservice/Dockerfile
docker-compose build
docker-compose down
docker-compose up --force-recreate
