package com.example.myapplication2

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.myapplication2.notification.MotivationReceiver
import com.example.myapplication2.notification.NotificationReceiver
import java.util.*

class nastroyki : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "my_channel_id"
            val channelName = "My Channel Name"
            val channelDescription = "My Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


        val themePreference = findPreference<ListPreference>("theme")
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val selectedValue = newValue as String
            when (selectedValue) {
                "dark" -> {
                    saveThemeSelection(AppCompatDelegate.MODE_NIGHT_YES)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                "light" -> {
                    saveThemeSelection(AppCompatDelegate.MODE_NIGHT_NO)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                else -> {
                    saveThemeSelection(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            true
        }

        val uvedki = findPreference<SwitchPreference>("enable_notifications")
        val motiv = findPreference<SwitchPreference>("reminder_switch")
        val napomin = findPreference<SwitchPreference>("reminder_switch1")
        val notificationIntent1 = Intent(context, MotivationReceiver::class.java)
        val pendingIntent1 = PendingIntent.getBroadcast(context, 0, notificationIntent1, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        motiv?.setOnPreferenceChangeListener { preference, newValue ->
            val isEnabled = newValue as Boolean

            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (isEnabled) {
                // уведомления включены
                val interval = 24 * 60 * 60 * 1000 // 24 часа
                val startTime = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, 9) // типо день когда
                }.timeInMillis
                Log.d(TAG, "Alarm is set to start at $startTime, with an interval of $interval milliseconds")
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, startTime,
                    interval.toLong(), pendingIntent1
                )
            } else {
                // уведомления выключены
                alarmManager.cancel(pendingIntent1)
            }
            true
        }
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(requireContext(), NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        napomin?.setOnPreferenceChangeListener { preference, newValue ->
            val isChecked = newValue as Boolean
            if (isChecked) {
                val interval =  1 * 1000 // 6 часов
                val startTime = System.currentTimeMillis()
                Log.d(TAG, "Alarm is set to start at $startTime, with an interval of $interval milliseconds")
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, startTime,
                    interval.toLong(), pendingIntent
                )
            } else {
                alarmManager.cancel(pendingIntent)
            }
            true
        }
        uvedki?.setOnPreferenceChangeListener { _, newValue ->
            val enableNotifications = newValue as Boolean
            motiv?.isEnabled = enableNotifications
            napomin?.isEnabled = enableNotifications
            true
        }



    }

    private fun saveThemeSelection(themeMode: Int) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.edit().putInt("theme_mode", themeMode).apply()
    }

}


