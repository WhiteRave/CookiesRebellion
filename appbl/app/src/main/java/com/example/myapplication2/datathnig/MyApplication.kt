package com.example.myapplication2.datathnig

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    lateinit var db: AppDatabase
    lateinit var taskDao: TaskDao

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        taskDao = db.taskDao()
    }
}
