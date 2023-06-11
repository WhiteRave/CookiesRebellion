package com.example.todo.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todo.domain.usecase.GetAllDayUseCase
import com.example.todo.domain.usecase.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    getAllDayUseCase: GetAllDayUseCase,
): ViewModel() {
    val days = runBlocking { getAllDayUseCase.invoke() }
    var isReady by mutableStateOf(false)

}