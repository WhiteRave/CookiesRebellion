package com.example.todo.presentation.Settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.data.local.model.Settings
import com.example.todo.domain.usecase.ChangeNotificationUseCase
import com.example.todo.domain.usecase.ChangeThemeUseCase
import com.example.todo.domain.usecase.GetSettingsUseCase
import com.example.todo.domain.usecase.InsertSettingsUseCase
import com.example.todo.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val insertSettingsUseCase: InsertSettingsUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val changeNotificationUseCase: ChangeNotificationUseCase
) : ViewModel() {
    val stateSettings = MutableStateFlow(Settings())

    init {
        viewModelScope.launch {
            getSettingsUseCase.invoke().let {
                if (it == null) {
                    insertSettingsUseCase.invoke(Settings())
                    Log.d("11", "нет настроек")
                } else {
                    stateSettings.emit(it)
                }
            }
        }

    }

    fun changeTheme(idTheme: Int) {
        viewModelScope.launch {
            changeThemeUseCase.invoke(idTheme).let {
                stateSettings.emit(
                    Settings(
                        id = stateSettings.value.id,
                        notification = stateSettings.value.notification,
                        language = stateSettings.value.language,
                        theme = idTheme,
                        format = stateSettings.value.format
                    )
                )
            }
        }
    }

    fun changeNotification(notification: Boolean) {
        viewModelScope.launch {
            changeNotificationUseCase.invoke(notification).let {
                stateSettings.emit(
                    Settings(
                        id = stateSettings.value.id,
                        notification = notification,
                        language = stateSettings.value.language,
                        theme = stateSettings.value.theme,
                        format = stateSettings.value.format
                    )
                )
            }
        }
    }
}