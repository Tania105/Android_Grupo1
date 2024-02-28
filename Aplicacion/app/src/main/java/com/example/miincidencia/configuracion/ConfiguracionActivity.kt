package com.example.miincidencia.configuracion

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.miincidencia.Dataclases.PerfilDataResponse
import com.example.miincidencia.MainActivity
import com.example.miincidencia.R
import com.example.miincidencia.databinding.ActivityConfiguracionBinding
import com.example.miincidencia.inicio.InicioActivity
import com.google.gson.Gson
import kotlinx.coroutines.MainScope

class ConfiguracionActivity : AppCompatActivity(){
    lateinit var binding: ActivityConfiguracionBinding
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var perfil : PerfilDataResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        encontrarPerfil()
        initUI()


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_generico, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            android.R.id.home -> {
                // Maneja el botón de retroceso
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun initUI(){
        // Habilita el botón de retroceso en la barra de acción
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.apply{
            title = "Configuración"
        }
        binding.correoElectronico.text=perfil.educantabria.toString()

        binding.txtCerrarSesion.setOnClickListener{

        }

        binding.txtContacto.setOnClickListener {
            val emailAddresses = arrayOf("directiva@iris.com", "programadores@iris.com")
            composeEmail(emailAddresses, "Consulta")
        }

        binding.swtOscuro.isChecked = isDarkModeEnabled()
        binding.swtOscuro.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)


        binding.txtCerrarSesion.setOnClickListener {
            cerrarSesion()
        }


    }
    private fun isDarkModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun composeEmail(addresses: Array<String>, subject: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, subject)
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // Manejo de error si no hay aplicaciones de correo electrónico instaladas
                throw Exception("No se encontró ninguna aplicación de correo electrónico instalada.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Muestra un mensaje de error al usuario
            Toast.makeText(this, "Error al abrir la aplicación de correo electrónico.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun encontrarPerfil(){
        // Obtener las preferencias compartidas
        val sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

        // Recuperar el perfil almacenado
        val perfilString = sharedPreferences.getString("perfil", null)
        // Verificar si se encontró un perfil almacenado
        if (perfilString != null) {
            // Si se encontró un perfil, puedes convertirlo de String a objeto PerfilDataResponse usando Gson
            val gson = Gson()
            perfil = gson.fromJson(perfilString, PerfilDataResponse::class.java)

            // Ahora puedes utilizar el perfil recuperado
            // Por ejemplo, puedes mostrar sus datos en la interfaz de usuario
            Log.d("TAG", "Perfil recuperado: $perfil")
            Thread.sleep(100)
        } else {
            // Si no se encontró ningún perfil almacenado, puedes manejar esta situación según sea necesario
            Log.d("TAG", "No se encontró ningún perfil almacenado")
        }
    }
    private fun cerrarSesion() {
        // Eliminar el estado de inicio de sesión
        val editor = sharedPreferences.edit()
        editor.remove("sesion_iniciada")
        editor.apply()

        // Abrir la actividad de inicio
        val intent = Intent(this, InicioActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Finalizar esta actividad para que el usuario no pueda volver atrás
    }
}