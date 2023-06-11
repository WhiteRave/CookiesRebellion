package com.example.todo.domain.usecase

import com.example.todo.data.local.repositoryImpl.SettingsRepositoryImpl
import javax.inject.Inject

class ChangeNotificationUseCase @Inject constructor(
    private val settingsRepositoryImpl: SettingsRepositoryImpl
) {
    suspend fun invoke(new: Boolean) = settingsRepositoryImpl.changeNotification(new)
}