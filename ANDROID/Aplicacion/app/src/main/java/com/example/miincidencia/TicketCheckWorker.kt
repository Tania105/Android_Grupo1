package com.example.miincidencia


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.pruebaandroid.InicioActivity.Companion.CHANNEL_ID
import java.util.concurrent.TimeUnit

class TicketCheckWorker(appContext: Context, params: WorkerParameters) : Worker(appContext, params) {
    //Clase que envia una notificacion con el titulo y descripcion de la notificacion
    override fun doWork(): Result {

        return Result.success()
    }














//    override fun doWork(): Result {
//        //Metodo que checkea la api para ver si los tickets han sido completados
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//
//        val repeatingRequest = PeriodicWorkRequestBuilder<TicketCheckWorker>(10, TimeUnit.MINUTES)
//            .setConstraints(constraints)
//            .build()
//
//        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
//            "TICKET_CHECK_WORK",
//            ExistingPeriodicWorkPolicy.KEEP,
//            repeatingRequest
//        )
//
//        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
//            //TODO: Cambiar el icono de la notificacion
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Ticket Completed")
//            .setContentText("Your ticket has been marked as completed.")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//
//        fun hasForegroundServicePermission(): Boolean {
//            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                PackageManager.PERMISSION_GRANTED == checkSelfPermission(Manifest.permission.FOREGROUND_SERVICE)
//            } else {
//                true
//            }
//        }
//
//        if (hasForegroundServicePermission()) {
//            with(NotificationManagerCompat.from(requireContext())) {
//                notify(1, builder.build())
//            }
//        }
//
//        return Result.success()
//    }


}