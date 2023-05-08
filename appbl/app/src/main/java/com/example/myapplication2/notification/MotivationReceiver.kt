package com.example.myapplication2.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication2.R

class MotivationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val elements = context.resources.openRawResource(R.raw.elements).bufferedReader().readLines()
        val randomIndex = (0 until elements.size).random()
        val randomElement = elements[randomIndex]

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "motivation_channel",
                "Мотивация",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, "motivation_channel")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Мотивация на сегодня")
            .setContentText(randomElement)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        notificationManager.notify(1, notificationBuilder.build())
    }
}
