package com.example.myapplication2


import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.datathnig.TaskAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*
import com.example.myapplication2.spisochek



class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themeMode = preferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(themeMode)


        setContentView(R.layout.activity_main)




        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener{task ->
            if (!task.isSuccessful){
                return@addOnCompleteListener
            }
            val token =task.result
            Log.e("TAG", "Token -> $token")




        }


        val navController = this.findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calendar -> {
                    navController.navigate(R.id.calendar)
                    true
                }
                R.id.Home -> {
                    navController.navigate(R.id.Home)
                    true
                }
                R.id.fullscreen -> {
                    navController.navigate(R.id.cheklist)
                    true
                }
                R.id.Settings -> {
                    navController.navigate(R.id.Settings)
                    true
                }
                else -> false
            }
        }




    }





}
