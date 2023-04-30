package com.example.myapplication2.datathnig

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class TaskRepository(private val context: Context) {
    private val gson = Gson()
    private val taskListType = object : TypeToken<List<TaskItem>>() {}.type
    private val taskFile = File(context.filesDir, "tasks.json")

    fun saveTasks(tasks: List<TaskItem>) {
        taskFile.writeText(gson.toJson(tasks))
    }

    fun loadTasks(): List<TaskItem> {
        if (!taskFile.exists()) {
            return emptyList()
        }
        return gson.fromJson(taskFile.readText(), taskListType)
    }
}
