#!/usr/bin/python
# coding=utf-8
from __future__ import print_function
import serial, struct, sys, time, json

CMD_MODE = 2
CMD_QUERY_DATA = 4
CMD_SLEEP = 6
MODE_QUERY = 1

ser = serial.Serial()
ser.port = "/dev/ttyUSB0"
ser.baudrate = 9600

ser.open()
ser.flushInput()

byte, data = 0, ""

def construct_command(cmd, data=[]):
    assert len(data) <= 12
    data += [0,]*(12-len(data))
    checksum = (sum(data)+cmd-2)%256
    ret = "\xaa\xb4" + chr(cmd)
    ret += ''.join(chr(x) for x in data)
    ret += "\xff\xff" + chr(checksum) + "\xab"
    return ret

def process_data(d):
    r = struct.unpack('<HHxxBB', d[2:])
    pm25 = r[0]/10.0
    pm10 = r[1]/10.0
    checksum = sum(ord(v) for v in d[2:8])%256
    return [pm25, pm10]

def read_response():
    byte = 0
    while byte != "\xaa":
        byte = ser.read(size=1)

    d = ser.read(size=9)
    return byte + d

def cmd_set_mode(mode=MODE_QUERY):
    ser.write(construct_command(CMD_MODE, [0x1, mode]))
    read_response()

def cmd_query_data():
    ser.write(construct_command(CMD_QUERY_DATA))
    d = read_response()
    values = []
    if d[1] == "\xc0":
        values = process_data(d)
    return values

def cmd_set_sleep(sleep=1):
    mode = 0 if sleep else 1
    ser.write(construct_command(CMD_SLEEP, [0x1, mode]))
    read_response()

def read_pm():
    cmd_set_sleep(0)
    cmd_set_mode(1)
    for t in range(2):
        time.sleep(5)
        values = cmd_query_data()
        if values is not None and values.count == 2:
            print("PM2.5: ", values[0], ", PM10: ", values[1])

    cmd_set_mode(0)
    cmd_set_sleep()

    return [values[0], values[1]]
