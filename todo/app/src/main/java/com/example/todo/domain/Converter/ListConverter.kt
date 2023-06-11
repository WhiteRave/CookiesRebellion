package com.example.todo.domain.Converter

import androidx.room.TypeConverter
import com.example.todo.data.local.model.ToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    @TypeConverter
    fun fromString(value: String): List<ToDo> {
        val listType = object : TypeToken<List<ToDo>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<ToDo>): String {
        return Gson().toJson(list)
    }
}