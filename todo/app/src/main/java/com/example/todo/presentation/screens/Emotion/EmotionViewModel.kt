package com.example.todo.presentation.screens.Emotion

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.local.model.Day
import com.example.todo.domain.usecase.ChangeEmotionUseCase
import com.example.todo.domain.usecase.GetAllDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import javax.inject.Inject

@HiltViewModel
class EmotionViewModel @Inject constructor(
    private val getAllDayUseCase: GetAllDayUseCase,
    private val changeEmotionUseCase: ChangeEmotionUseCase
) : ViewModel() {
    val days = MutableLiveData<List<Day>>()
    var isAdd by mutableStateOf(false)

    init {
        viewModelScope.launch { days.postValue(getAllDayUseCase.invoke()) }
    }

    fun compareDates(date1: String, date2: String): Int {
        val formatter = DateTimeFormatterBuilder()
            .appendPattern("dd-MM-uuuu")
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .toFormatter()

        val localDate1 = LocalDate.parse(date1, formatter)
        val localDate2 = LocalDate.parse(date2, formatter)

        return localDate1.compareTo(localDate2)
    }

    fun changeEmotion(emotion: String, date: String) {
        val newDays = days.value!!.map {
            if (it.date == date) {
                Day(date = it.date,
                    motivation = it.motivation,
                    emotion = emotion,
                    priorityToDos = it.priorityToDos,
                    toDos = it.toDos)
            } else {
                it
            }
        }
        days.postValue(newDays)
        viewModelScope.launch {
            changeEmotionUseCase.invoke(emotion, date)
        }
    }

    fun changeIsAdd() {
        isAdd = !isAdd
    }
}