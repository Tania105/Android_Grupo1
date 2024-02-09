package com.example.pruebaandroid

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.miincidencia.MainActivity

import com.example.miincidencia.R

class InicioActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_inicio)

        // Call this method to create the notification channel
        createNotificationChannel()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            sendNotification(this, "Prueba", "Esto es una prueba")
        }

        //The btnInicioSesion is the button that will take us to the Main Interface
        val btnInicioSesion = findViewById<Button>(R.id.btnInicioSesion)
        btnInicioSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}