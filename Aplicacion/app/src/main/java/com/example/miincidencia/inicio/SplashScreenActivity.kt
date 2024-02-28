package com.example.miincidencia.inicio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.miincidencia.MainActivity
import com.example.miincidencia.R

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 2000 // Tiempo de duración de la pantalla de bienvenida

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Simula una condición (por ejemplo, si el usuario está logueado)
        val conditionMet = true

        Handler().postDelayed({
            // Si la condición se cumple, ve a la ActivityA, de lo contrario ve a la ActivityB
            val intent = if (conditionMet) {
                Intent(this@SplashScreenActivity, InicioActivity::class.java)
            } else {
                Intent(this@SplashScreenActivity, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}