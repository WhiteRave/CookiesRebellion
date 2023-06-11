package com.example.todo.data.local.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.data.local.model.Settings


@Dao
interface SettingsRepository {
    @Insert
    suspend fun insertSettings(settings: Settings)

    @Query("select * from settings WHERE id=0")
    suspend fun getSettings(): Settings

    @Query("UPDATE settings set notification=:newNot where id=0")
    suspend fun changeNotification(newNot: Boolean)

    @Query("UPDATE settings set theme=:newTheme where id=0")
    suspend fun changeTheme(newTheme: Int)
}