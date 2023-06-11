package com.example.todo.presentation.screens.Calendar

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.local.model.Day
import com.example.todo.data.local.model.ToDo
import com.example.todo.domain.usecase.ChangeToDosUseCase
import com.example.todo.domain.usecase.GetAllDayUseCase
import com.example.todo.domain.usecase.GetCurrentDateUseCase
import com.example.todo.domain.usecase.GetDayByDateUseCase
import com.example.todo.domain.usecase.InsertDayUseCase
import com.example.todo.presentation.screens.Main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getDayByDateUseCase: GetDayByDateUseCase,
    private val changeToDosUseCase: ChangeToDosUseCase,
    private val insertDayUseCase: InsertDayUseCase,
    private val getAllDayUseCase: GetAllDayUseCase,
) : ViewModel() {
    val calendar = Calendar.getInstance()
    var day by mutableStateOf(0)
    var isAdd by mutableStateOf(false)


    private val _toDos = MutableLiveData<List<ToDo>>()
    val toDos: LiveData<List<ToDo>>
        get() = _toDos


    fun getDate(dayOfMonth: Int): String {
        val date =
            "$dayOfMonth-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}".split("-")
                .toMutableList()
        if (date[0].length == 1) {
            date[0] = "0${date[0]}"
        }
        if (date[1].length == 1) {
            date[1] = "0${date[1]}"
        }

        return "${date[0]}-${date[1]}-${date[2]}"
    }

    fun currentDay(dayOfMonth: Int) {
        day = dayOfMonth
        if (calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {

        }
        val date = getDate(dayOfMonth)
        Log.d("11", date)
        viewModelScope.launch {
            var day = getDayByDateUseCase.invoke(date)
            if (day == null) {

                Log.d("11", "опачки")
                day = Day(
                    date = date
                )
                insertDayUseCase.invoke(day)
                _toDos.postValue(day.toDos)
            } else {
                _toDos.postValue(day.toDos)
            }
        }
    }

    fun addToDo(title: String) {
        viewModelScope.launch {
            val date = getDate(day)
            getDayByDateUseCase.invoke(date).let {
                val newToDos = it.toDos + ToDo(title = title, date = date)
                changeToDosUseCase.invoke(newToDos, date = date)
                _toDos.postValue(newToDos)
            }
        }
    }

    fun deleteToDo(toDo: ToDo) {
        viewModelScope.launch {
            val newToDos = _toDos.value?.filter { it != toDo }
            newToDos?.let { changeToDosUseCase.invoke(it, getDate(day)) }
            _toDos.postValue(newToDos)
        }
    }

    fun updateToDo(toDo: ToDo) {
        viewModelScope.launch {
            //getDayByDateUseCase.invoke(date = currentDate)
            val newToDos = _toDos.value!!.map {
                if (it.title == toDo.title) {
                    toDo
                } else {
                    it
                }
            }
            changeToDosUseCase.invoke(newToDos, getDate(day))
            _toDos.postValue(newToDos)
        }
    }

    fun changeIsAdd() {
        isAdd = !isAdd
    }


}