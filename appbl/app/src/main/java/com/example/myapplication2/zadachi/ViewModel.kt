package com.example.myapplication2.zadachi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ViewModel : ViewModel() {
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





}