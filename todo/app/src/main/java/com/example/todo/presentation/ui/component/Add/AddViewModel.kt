package com.example.todo.presentation.ui.component.Add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddViewModel: ViewModel() {
    var title by mutableStateOf("")

    fun setText(text: String) {
        title = text
    }
}