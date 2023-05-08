package com.example.myapplication2.zadachi

import android.content.Context
import com.example.myapplication2.datathnig.TaskItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class TaskRepository1(private val context: Context) {
    private val gson = Gson()
    private val taskListType = object : TypeToken<List<Taskotem>>() {}.type
    private val taskFile = File(context.filesDir, "tasks2.json")
    fun saveTasks(tasks: List<Taskotem>) {
        taskFile.writeText(gson.toJson(tasks))
    }

    fun loadTasks(): List<Taskotem> {
        if (!taskFile.exists()) {
            return emptyList()
        }
        return gson.fromJson(taskFile.readText(), taskListType)
    }



}