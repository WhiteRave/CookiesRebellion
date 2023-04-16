package com.example.myapplication2.datathnig

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TaskItem::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
