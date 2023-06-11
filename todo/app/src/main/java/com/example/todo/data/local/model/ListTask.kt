package com.example.todo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.todo.domain.Converter.ListConverter

@Entity(tableName = "listtask")
@TypeConverters(ListConverter::class)
class ListTask (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var toDos: List<ToDo> = emptyList()
)