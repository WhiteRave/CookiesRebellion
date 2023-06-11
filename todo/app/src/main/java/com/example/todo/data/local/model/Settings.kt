package com.example.todo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.jvm.internal.Ref.BooleanRef

@Entity(tableName = "settings")
class Settings (
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val notification: Boolean = false,
    val language: String = "Русский",
    val theme: Int = 0,
    val format: String = "dd-mm-yyyy"
)