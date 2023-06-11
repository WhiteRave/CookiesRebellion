package com.example.todo.data.local.repositoryImpl

import com.example.todo.data.local.model.Settings
import com.example.todo.data.local.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun insertSettings(settings: Settings) = settingsRepository.insertSettings(settings = settings)

    suspend fun getSettings(): Settings = settingsRepository.getSettings()

    suspend fun changeNotification(new: Boolean) = settingsRepository.changeNotification(new)

    suspend fun changeTheme(new: Int) = settingsRepository.changeTheme(new)
}