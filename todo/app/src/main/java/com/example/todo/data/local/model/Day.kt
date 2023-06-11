package com.example.todo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.todo.domain.Converter.ListConverter

@Entity(tableName = "day")
@TypeConverters(ListConverter::class)
data class Day(
    @PrimaryKey(autoGenerate = false)
    val date: String = "",
    var motivation: String = "",
    var emotion: String = "",
    val priorityToDos: List<ToDo> = emptyList(),
    var toDos: List<ToDo> = emptyList()

) {
//    companion object {
//        private val motivationList =
//            listOf("Поверь в себя и во все, что ты есть. Знай, \nчто внутри тебя есть что-то больше \nлюбого препятствия.")
//    }

//    init {
//        motivation = motivationList[(motivationList.indices).random()]
//    }
}