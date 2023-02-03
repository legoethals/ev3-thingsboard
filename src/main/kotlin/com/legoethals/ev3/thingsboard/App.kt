package com.legoethals.ev3.thingsboard

import lejos.hardware.BrickFinder
import lejos.hardware.Sound
import lejos.hardware.ev3.EV3
import lejos.hardware.ev3.LocalEV3.ev3
import lejos.hardware.motor.EV3MediumRegulatedMotor
import lejos.hardware.port.Port
import lejos.internal.ev3.EV3LED
import org.fusesource.mqtt.client.Future
import org.fusesource.mqtt.client.MQTT
import org.fusesource.mqtt.client.QoS
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration


fun main() {

    val eV3 = BrickFinder.getLocal() as EV3
    Sound.playTone(220, 200, 10)
    val ev3Led = eV3.led as EV3LED
    ev3Led.setPattern(EV3LED.PATTERN_HEARTBEAT * 2 + EV3LED.COLOR_RED)
    println("Starting motor...")
    val motor = EV3MediumRegulatedMotor(ev3.getPort("B"))
    motor.setSpeed(motor.maxSpeed)
    motor.forward()

    //https://hivemq.github.io/hivemq-mqtt-client/
    val topic = "v1/devices/me/telemetry"
    val host = "localhost"
    val port = 1883
    //mosquitto_pub -d -q 1 -h "localhost" -p "1883" -t "" -u "" -m {"temperature":6}

    println("Setting up mqtt connection")
    val mqtt = MQTT()
    mqtt.setHost(host, port)
    mqtt.setUserName("ev3-1")
    mqtt.setPassword("ev3-1")
    mqtt.setClientId("ev3-1")
    val connection = mqtt.futureConnection()
    val f1: Future<Void> = connection.connect()
    println("Await to connect...")
    f1.await()

    Flux.interval(Duration.ofSeconds(1)).flatMap {
        Mono.fromCallable {
            val message = """{"light": $it}"""
            val f3 = connection.publish(topic, message.toByteArray(), QoS.AT_LEAST_ONCE, false)
            f3.await()
        }.subscribeOn(Schedulers.boundedElastic())
    }.take(5).blockLast()

    motor.stop()

    connection.disconnect().await()
}
