plugins {
    kotlin("jvm") version "1.6.0"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.6"
    id("com.legoethals.ev3.d3v") version "1.0.0"
}

ev3 {
    mainClass.set("com.legoethals.ev3.thingsboard.AppKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.fusesource.mqtt-client:mqtt-client:1.12")
    implementation("com.hivemq:hivemq-mqtt-client-reactor:1.3.0")
    implementation("io.projectreactor:reactor-core:3.4.14")
}
