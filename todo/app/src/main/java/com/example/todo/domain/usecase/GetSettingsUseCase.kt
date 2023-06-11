package com.example.todo.domain.usecase

import com.example.todo.data.local.model.Settings
import com.example.todo.data.local.repositoryImpl.SettingsRepositoryImpl
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepositoryImpl: SettingsRepositoryImpl
) {
    suspend fun invoke(): Settings = settingsRepositoryImpl.getSettings()
}