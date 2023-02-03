package com.legoethals.ev3.thingsboard

import lejos.hardware.Button
import lejos.hardware.Sound
import lejos.hardware.ev3.LocalEV3
import lejos.hardware.lcd.LCD
import lejos.hardware.motor.EV3MediumRegulatedMotor

fun main() {
    Sound.beepSequenceUp()

    val motor = EV3MediumRegulatedMotor(LocalEV3.ev3.getPort("B"))
    motor.setSpeed(motor.maxSpeed)
    motor.backward()

    LCD.clear()
    LCD.drawString("Enter to Exit", 0, 2)
    Button.ENTER.waitForPressAndRelease()

    motor.stop()
}