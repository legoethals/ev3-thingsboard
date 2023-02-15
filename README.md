# ev3-thingsboard

Default credentials:

    System Administrator: sysadmin@thingsboard.org / sysadmin
    Tenant Administrator: tenant@thingsboard.org / tenant
    Customer User: customer@thingsboard.org / customer

EV3 device Access token:
43nIbGhOQlT7MPZejRO8

mosquitto_pub -d -q 1 -h "localhost" -p "1883" -t "v1/devices/me/telemetry" -u "ev3-1" -m {"light":25}

Mqtt Port forwarding
`ssh -R 10.0.1.1:1883:localhost:1883 root@ev2_usb`
``````
