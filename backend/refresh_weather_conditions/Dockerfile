FROM python:2.7
ADD refresh_weather_conditions.py /
RUN pip install httplib2 psycopg2
ENV CITY_NAME=[PUT CITY NAME HERE, e.g. Bydgoszcz]
ENV LAT=[PUT LATITUDE HERE, e.g. 53.1256049]
ENV LON=[PUT LONGITUDE HERE, e.g 17.8981005]
ENV AIRLY_API_KEY=[PUT AIRLY API KEY HERE]
ENV OWM_API_KEY=[PUT OPEN WEATHER MAPS API KEY HERE]
ENV OWM_LANGUAGE=en
ENV OWM_UNITS=metric
CMD ["python", "-u", "refresh_weather_conditions.py"]
