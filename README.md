# ev3-thingsboard

Default credentials:

    System Administrator: sysadmin@thingsboard.org / sysadmin
    Tenant Administrator: tenant@thingsboard.org / tenant
    Customer User: customer@thingsboard.org / customer

EV3 device Access token:
ZL8PFbKV3lKXKUlFx6qN

mosquitto_pub -d -q 1 -h "localhost" -p "1883" -t "v1/devices/me/telemetry" -u "ZL8PFbKV3lKXKUlFx6qN" -m {"temperature":25}
