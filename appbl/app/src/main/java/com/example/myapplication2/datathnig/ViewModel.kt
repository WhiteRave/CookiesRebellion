package com.example.myapplication2.datathnig

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel : ViewModel() {
    val inputText = MutableLiveData<String>()
    val tasks: MutableLiveData<List<String>> = MutableLiveData()
    val id: String = UUID.randomUUID().toString()
    init {
        tasks.value = mutableListOf()
    }


    fun addTask(task: String) {
        val currentTasks = tasks.value?.toMutableList() ?: mutableListOf()
        currentTasks.add(task)
        tasks.value = currentTasks.toList()
        tasks.value = tasks.value
    }

    fun updateTasks(taskText: String) {
        tasks.value = listOf(taskText)
    }





}

