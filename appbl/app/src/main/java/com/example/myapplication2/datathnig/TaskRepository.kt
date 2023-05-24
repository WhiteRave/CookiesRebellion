package com.example.myapplication2.datathnig

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class TaskRepository(private val context: Context) {
    private val gson = Gson()
    private val taskListType = object : TypeToken<List<TaskItem>>() {}.type
    private val taskFile = File(context.filesDir, "tasks1.json")
    fun saveTasks(tasks: List<TaskItem>) {
        taskFile.writeText(gson.toJson(tasks))
    }

    fun loadTasks(): List<TaskItem> {
        if (!taskFile.exists()) {
            return emptyList()
        }
        val json = taskFile.readText()
        if (json.isEmpty()) {
            return emptyList()
        }
        return gson.fromJson(json, taskListType)
    }
    fun deleteTask(taskItem: TaskItem) {
        val taskList = loadTasks().toMutableList()
        taskList.remove(taskItem)
        saveTasks(taskList)
    }

}
