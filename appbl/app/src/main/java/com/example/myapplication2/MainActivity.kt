package com.example.myapplication2


import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*


class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1
    private var isFragmentOpen = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fullscreen) as? cheklist
            fragment?.let {
                if (!it.isAdded) {
                    // Фрагмент был удален из бэкстека и полностью уничтожен
                    it.onDestroy()
                }
            }
        }




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
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        if (navController.currentDestination?.id == R.id.BlankFragment) {
            navController.navigate(R.id.cheklist)
        } else {
            super.onBackPressed()
        }
    }







}
