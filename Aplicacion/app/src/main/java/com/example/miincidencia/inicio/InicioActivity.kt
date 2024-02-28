package com.example.miincidencia.inicio

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.miincidencia.Dataclases.PerfilDataResponse
import com.example.miincidencia.MainActivity
import com.example.miincidencia.R
import com.example.miincidencia.api.ApiService
import com.example.miincidencia.databinding.ActivityInicioBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep

class InicioActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding : ActivityInicioBinding
    lateinit var retrofit: Retrofit
    var perfil: PerfilDataResponse? = null
    companion object {
        const val LOCATION_REQUEST_CODE =  1000 // Choose a unique number for your request code
        const val CHANNEL_ID : String = "your_channel_id"

    }

    @SuppressLint("SuspiciousIndentation")
    private fun createNotificationChannel() {
        val channelName = "Your Channel Name"
        val channelDescription = "Channel Description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance).apply {
            description = channelDescription
        }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun sendNotification(context: Context, title: String, description: String) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your notification icon
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), LOCATION_REQUEST_CODE)
        } else {
            // Permission already granted, proceed with the operation
            with(NotificationManagerCompat.from(context)) {
                notify(System.currentTimeMillis().toInt(), builder.build())
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit=getRetrofit()
        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

        if (usuarioYaInicioSesion()) {
            // Si el usuario ya ha iniciado sesión, ir directamente a MainActivity
            irAMainActivity()
        }

        // Call this method to create the notification channel
        createNotificationChannel()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            sendNotification(this, "Prueba", "Esto es una prueba")
        }

        binding.btnInicioSesion.setOnClickListener {
            // Obtener los valores de usuario y contraseña
            val usuario = binding.edtUsuario.editText?.text.toString()
            val password = binding.edtPassword.editText?.text.toString()

            // Aquí debes realizar la validación de los datos de inicio de sesión
            // Por ejemplo, puedes comparar con datos almacenados en una base de datos o en algún servicio remoto
            // En este ejemplo, vamos a comparar con valores estáticos


            inicioSesion(usuario,password)
            // Si los datos son correctos, puedes abrir la siguiente actividad o realizar alguna acción adicional
            sleep(200)
            if(perfil!=null){
                guardarInicioSesion()
                irAMainActivity()
            }else{
                binding.edtUsuario.error = "Usuario incorrecto o contraseña incorrecta"
            }

            // Si los datos son incorrectos, muestra un mensaje de error y cambia el color del borde del TextInputLayout


        }

    }


    private fun irAMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finalizar la actividad actual para que el usuario no pueda volver atrás con el botón de retroceso
    }

    private fun guardarInicioSesion() {
        // Marcar el inicio de sesión como realizado
        val editor = sharedPreferences.edit()
        // Serializar el objeto de perfil a una cadena JSON utilizando Gson
        val gson = Gson()
        val perfilString = gson.toJson(perfil)

        // Guardar la cadena JSON del perfil en las preferencias compartidas
        editor.putString("perfil", perfilString)
        editor.putBoolean("sesion_iniciada", true)
        editor.apply()
    }

    private fun usuarioYaInicioSesion(): Boolean {
        // Verificar si el inicio de sesión ya ha sido realizado previamente
        return sharedPreferences.getBoolean("sesion_iniciada", false)
    }
    fun inicioSesion(educantabria: String, password:String){
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Retrofit", "Intentando iniciar sesión: educantabria=$educantabria, password=$password")
            val myResponse: Response<PerfilDataResponse> = retrofit.create(ApiService::class.java)
                .getPerfilByEducantabriaAndPass(educantabria, password)
            if (myResponse.isSuccessful){
                val response : PerfilDataResponse?= myResponse.body()
                if (response!=null){
                    Log.i("Retrofit", "Inicio de sesión exitoso")
                    Log.i("Retrofit", "Respuesta: $response")
                    perfil=response

                }
            } else {

                val errorMessage = myResponse.errorBody()?.string()
                Log.e("Retrofit", "Error al iniciar sesión - Código: ${myResponse.code()}, Mensaje: $errorMessage")
            }
        }
    }



}

public fun getRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl("http://10.0.2.2:8089/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()


}


