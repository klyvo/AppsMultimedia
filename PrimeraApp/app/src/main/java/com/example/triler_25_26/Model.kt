package com.example.triler_25_26

import java.util.Random

open class Model(var numCubs: Int = 3) {
    var playing: Boolean = false
    var posicionBall: Int = 0
    private val random = Random()

    var aciertos: Int = 0
    var fallos: Int = 0

    fun initGame() {
        startGame(numCubs)
    }

    fun startGame(cubs: Int) {
        numCubs = cubs
        posicionBall = random.nextInt(numCubs) + 1
        playing = true
    }

    fun jugar(numeroCubilete: Int): Boolean {
        if (!playing) return false
        playing = false

        return if (numeroCubilete == posicionBall) {
            aciertos++
            true
        } else {
            fallos++
            false
        }
    }
}
