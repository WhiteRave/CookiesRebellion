package com.example.myapplication2.datathnig

data class TaskItem(
    var name: String,
    var isChecked: Boolean = false,
    var id: Int? = null,
    var hidden: Boolean = false
)
