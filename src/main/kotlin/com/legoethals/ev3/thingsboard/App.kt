package com.legoethals.ev3.thingsboard

import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.reactor.Mqtt3ReactorClient
import com.hivemq.client.mqtt.mqtt5.reactor.Mqtt5ReactorClient
import org.fusesource.mqtt.client.Future
import org.fusesource.mqtt.client.MQTT
import org.fusesource.mqtt.client.QoS
import reactor.core.publisher.Flux
import java.time.Duration


fun main() {
    //Check https://hivemq.github.io/hivemq-mqtt-client/
//    Sound.beep()
    val topic = "v1/devices/me/telemetry"
    val host = "localhost"
    val port = 1883
    //mosquitto_pub -d -q 1 -h "localhost" -p "1883" -t "" -u "" -m {"temperature":6}

    val client = MqttClient.builder()
        .useMqttVersion5()
        .identifier("ev3-1")
        .serverHost("localhost")
        .serverPort(1883)

//        .sslWithDefaultConfig()
        .buildRx()
    val reactorClient = Mqtt5ReactorClient.from(client)
    val brol = reactorClient.connectWith()
        .simpleAuth()
        .username("ev3-1")
        .password("ev3-1".toByteArray())
        .applySimpleAuth()
        .applyConnect()
        .block()






    val mqtt = MQTT()
    mqtt.setHost(host, port)
    mqtt.setUserName("ev3-1")
    mqtt.setPassword("ev3-1")
    mqtt.setClientId("ev3-1")
    val connection = mqtt.futureConnection()
    val f1: Future<Void> = connection.connect()
    f1.await()
    Flux.interval(Duration.ofSeconds(1)).doOnNext {
        val message = """{"temperature": $it}"""
        val f3 = connection.publish(topic, message.toByteArray(), QoS.AT_LEAST_ONCE, false)
        f3.await()
    }.take(20).blockLast()

    val f4 = connection.disconnect()
    f4.await()
}
