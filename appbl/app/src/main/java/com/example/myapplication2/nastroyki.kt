package com.example.myapplication2

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.example.myapplication2.notification.NotificationReceiver

class nastroyki : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

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
        uvedki?.setOnPreferenceChangeListener { preference, newValue ->
            val isEnabled = newValue as Boolean
            if (isEnabled) {
                // уведомления включены
                // здесь вы можете вызвать код для отправки уведомлений
            } else {
                // уведомления выключены
                NotificationManagerCompat.from(requireContext()).cancelAll()
            }
            true
        }
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(requireContext(), NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        napomin?.setOnPreferenceChangeListener { preference, newValue ->
            val isChecked = newValue as Boolean
            if (isChecked) {
                val interval =  6 * 60 * 60 * 1000 // 6 часов
                val startTime = System.currentTimeMillis()
                Log.d(TAG, "Alarm is set to start at $startTime, with an interval of $interval milliseconds")
                alarmManager.setInexactRepeating(
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


