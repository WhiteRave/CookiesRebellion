package com.example.todo.domain.usecase

import com.example.todo.data.local.repositoryImpl.SettingsRepositoryImpl
import javax.inject.Inject

class ChangeThemeUseCase @Inject constructor(
    private val settingsRepositoryImpl: SettingsRepositoryImpl
) {
    suspend fun invoke(new: Int) = settingsRepositoryImpl.changeTheme(new)
}