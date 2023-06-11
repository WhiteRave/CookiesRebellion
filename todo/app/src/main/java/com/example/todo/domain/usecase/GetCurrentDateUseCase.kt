package com.example.todo.domain.usecase

import java.util.Calendar

class GetCurrentDateUseCase {
    fun invoke(): String {
        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1 // Важно: значения месяца начинаются с 0, поэтому нужно добавить 1
        val year = calendar.get(Calendar.YEAR)
        val date = "$dayOfMonth-$month-$year".split("-").toMutableList()
        if (date[0].length == 1 ) {
            date[0] = "0${date[0]}"
        }
        if (date[1].length == 1 ) {
            date[1] = "0${date[1]}"
        }

        return "${date[0]}-${date[1]}-${date[2]}"
    }
}