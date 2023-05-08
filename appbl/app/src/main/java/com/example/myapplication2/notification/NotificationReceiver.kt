package com.example.myapplication2.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication2.R

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "my_channel_id"
        val channelName = "My Channel Name"
        val channelDescription = "My Channel Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = channelDescription
            notificationManager.createNotificationChannel(channel)
        }

        // Add your message here
        val message = "У вас есть невыполненные задачи"

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("Напоминание")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())
    }
}

