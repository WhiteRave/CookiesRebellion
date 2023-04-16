package com.example.myapplication2.datathnig


data class TaskItem(
    var name: String,
    var isChecked: Boolean = false,
    var itemId: Int = 0
)