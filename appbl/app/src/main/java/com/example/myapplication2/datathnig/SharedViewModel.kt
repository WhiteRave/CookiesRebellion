package com.example.myapplication2.datathnig

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel : ViewModel() {
    val inputText = MutableLiveData<String>()
    val tasks: MutableLiveData<List<String>> = MutableLiveData()
    val deletedTasks: MutableLiveData<List<String>> = MutableLiveData()
    val id: String = UUID.randomUUID().toString()
    init {
        tasks.value = mutableListOf()
        deletedTasks.value = mutableListOf()
    }

    fun addTask(task: String) {
        val currentTasks = tasks.value?.toMutableList() ?: mutableListOf()
        currentTasks.add(task)
        tasks.value = currentTasks.toList()
    }

    fun updateTasks(taskText: String) {
        tasks.value = listOf(taskText)
    }

    fun deleteTask(task: String) {
        val currentTasks = tasks.value?.toMutableList() ?: mutableListOf()
        currentTasks.remove(task)
        tasks.value = currentTasks.toList()

        val deletedTasksList = deletedTasks.value?.toMutableList() ?: mutableListOf()
        deletedTasksList.add(task)
        deletedTasks.value = deletedTasksList.toList()
    }
}
