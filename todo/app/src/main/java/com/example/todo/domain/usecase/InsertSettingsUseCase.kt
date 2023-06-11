package com.example.todo.domain.usecase

import androidx.room.Insert
import com.example.todo.data.local.model.Settings
import com.example.todo.data.local.repositoryImpl.SettingsRepositoryImpl
import javax.inject.Inject

class InsertSettingsUseCase @Inject constructor(
    private val settingsRepositoryImpl: SettingsRepositoryImpl
) {
    suspend fun invoke(settings: Settings) = settingsRepositoryImpl.insertSettings(settings = settings)
}