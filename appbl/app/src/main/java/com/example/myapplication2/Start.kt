 package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

 class Start : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         val preferences = PreferenceManager.getDefaultSharedPreferences(this)
         val themeMode = preferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
         AppCompatDelegate.setDefaultNightMode(themeMode)

         setContentView(R.layout.activity_start)

         val btnHideSplash = findViewById<Button>(R.id.btn_hide_splash)

         btnHideSplash.setOnClickListener {
             val intent = Intent(this, MainActivity::class.java)

             startActivity(intent)

             finish()
         }
     }
 }
