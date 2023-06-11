package com.example.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo.data.local.model.Day
import com.example.todo.data.local.model.ListTask
import com.example.todo.data.local.model.Settings
import com.example.todo.data.local.model.ToDo
import com.example.todo.data.local.repository.DayRepository
import com.example.todo.data.local.repository.ListTaskRepository
import com.example.todo.data.local.repository.SettingsRepository
import com.example.todo.domain.Converter.ListConverter

@Database(entities = [Day::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dayDao(): DayRepository
}

@Database(entities = [ListTask::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase2: RoomDatabase() {
    abstract fun listTaskDao(): ListTaskRepository
}

@Database(entities = [Settings::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase3: RoomDatabase() {
    abstract fun settingsDao(): SettingsRepository
}