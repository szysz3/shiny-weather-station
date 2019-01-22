FROM jsurf/rpi-raspbian
ADD webservice.py /
ADD sds.py /
ADD start.sh /
ADD photos /var/www/html/photos
RUN apt-get update && apt-get install -y --no-install-recommends \
    python \
    python-pip \
    python-setuptools \
    python-dev \
    python-smbus \
    libpq-dev \
    build-essential \
    iproute2 \
    git \
    apache2 \
    && apt-get clean
RUN pip install Flask psycopg2 pyserial
RUN git clone https://github.com/adafruit/Adafruit_Python_BMP.git \
    && cd Adafruit_Python_BMP && python setup.py install
RUN git clone https://github.com/adafruit/Adafruit_Python_DHT \
    && cd Adafruit_Python_DHT && python setup.py install
EXPOSE 5000 80
ENV HOST_IP_ADDR=%HOST_IP_ADDR%
CMD ["./start.sh"]
