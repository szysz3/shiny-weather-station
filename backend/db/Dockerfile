FROM postgres
ENV POSTGRES_USER pi
ENV POSTGRES_PASSWORD pipassword123
ENV POSTGRES_DB shinyweather
COPY init_db.sql /docker-entrypoint-initdb.d/
