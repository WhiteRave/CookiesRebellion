package com.example.todo.presentation.ui.component.ListItem

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.data.local.model.ListTask
import com.example.todo.data.local.model.ToDo
import com.example.todo.domain.usecase.ChangeToDosListTaskUseCase
import com.example.todo.domain.usecase.GetListTaskByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListItemViewModel @Inject constructor(
    private val changeToDosListTaskUseCase: ChangeToDosListTaskUseCase,
    private val getListTaskByIdUseCase: GetListTaskByIdUseCase
) : ViewModel() {
    val toDos = MutableLiveData<List<ToDo>>()
    var isAdd by mutableStateOf(false)
    var id: Long by mutableStateOf(-1)
    var name by mutableStateOf("")

    fun invoke(idTask: Long?) {
        id = idTask!!
        viewModelScope.launch {
            getListTaskByIdUseCase.invoke(idTask).let {
                toDos.postValue(it.toDos)
                name = it.name
            }
        }
    }

    fun changeIsAdd() {
        isAdd = !isAdd
    }

    fun deleteToDo(toDo: ToDo) {
        viewModelScope.launch {
            val newToDos = toDos.value?.filter { it != toDo }
            newToDos?.let { changeToDosListTaskUseCase.invoke(it, id) }
            toDos.postValue(newToDos)
        }
    }

    fun addToDo(title: String) {
        viewModelScope.launch {
            getListTaskByIdUseCase.invoke(id).let {
                val newToDOs = it.toDos + ToDo(title = title)
                changeToDosListTaskUseCase.invoke(newToDOs, id)
                toDos.postValue(newToDOs)
            }
        }
    }

    fun updateToDo(toDo: ToDo) {
        viewModelScope.launch {
            val newToDos = toDos.value!!.map {
                if (it.title == toDo.title) {
                    toDo
                } else {
                    it
                }
            }
            changeToDosListTaskUseCase.invoke(newToDos, id). let {
                toDos.postValue(newToDos)
            }
        }
    }
}