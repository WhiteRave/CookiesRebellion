package com.example.todo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ToDo(
    val title: String= "",
    val date: String = "",
    val priority: Boolean = false,
    var done: Boolean = false
)