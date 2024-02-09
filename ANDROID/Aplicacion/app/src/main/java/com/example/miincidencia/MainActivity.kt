package com.example.miincidencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIncidencia = findViewById<Button>(R.id.btnIncidencias)
        btnIncidencia.setOnClickListener {
            val intent = Intent(this, IncidenciaActivity::class.java)
            startActivity(intent)
        }
        val btnContacto = findViewById<Button>(R.id.btnContacto)
        btnContacto.setOnClickListener {
            val intent = Intent(this, ContactoActivity::class.java)
            startActivity(intent)
        }
        val btnConfig = findViewById<Button>(R.id.btnConfig)
        btnConfig.setOnClickListener {
            val intent = Intent(this, ConfiguracionActivity::class.java)
            startActivity(intent)
        }

    }
    //TODO: Crear metodo para recuperar los datos de la API


}