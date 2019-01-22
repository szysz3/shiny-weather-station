#!/bin/bash
/etc/init.d/apache2 start
export FLASK_APP=webservice.py
flask run --host=0.0.0.0
