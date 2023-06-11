package com.example.todo.presentation.screens.List

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.todo.data.local.model.ListTask
import com.example.todo.data.local.model.ToDo
import com.example.todo.domain.usecase.ChangeToDosListTaskUseCase
import com.example.todo.domain.usecase.DeleteTaskUseCase
import com.example.todo.domain.usecase.GetAllListTaskUseCase
import com.example.todo.domain.usecase.GetListTaskByIdUseCase
import com.example.todo.domain.usecase.InsertListTaskUseCase
import com.example.todo.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
@HiltViewModel
class ListViewModel @Inject constructor(
    private val getAllListTaskUseCase: GetAllListTaskUseCase,
    private val insertListTaskUseCase: InsertListTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    val listTask = MutableLiveData<List<ListTask>>()
    var isAdd by mutableStateOf(false)
    var title by mutableStateOf("")
    val itemList = MutableStateFlow(ListTask())

    init {
        viewModelScope.launch {
            getAllListTaskUseCase.invoke().let {
                listTask.postValue(it)
            }
        }
    }

    fun postItem(item: ListTask, navController: NavController) {
        viewModelScope.launch {
            itemList.emit(item).let {
                navController.navigate(
                    route = "listItem_screen/${item.id.toString()}"
                )
            }
        }
    }

    fun setText(it: String) {
        title = it
    }

    fun changeIsAdd() {
        isAdd = !isAdd
    }

    fun deleteList(list: ListTask) {
        viewModelScope.launch {
            val newListTask = listTask.value?.filter { it != list }
            deleteTaskUseCase.invoke(id = list.id)
            listTask.postValue(newListTask)
        }
    }



    fun createList() {
        viewModelScope.launch {
            val ls = ListTask(name = title, id = "${listTask.value!!.size+1}".toLong())
            val newListTask = listTask.value?.plus(ls)
            insertListTaskUseCase.invoke(ls).let {
                listTask.postValue(newListTask)
            }
            Log.d("11", "${newListTask!![newListTask.size-1].name.toString()} ${newListTask[newListTask.size-1].id}")
            title = ""
        }
    }
}