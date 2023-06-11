package com.example.todo.data.local.repositoryImpl

import com.example.todo.data.local.model.Day
import com.example.todo.data.local.model.ToDo
import com.example.todo.data.local.repository.DayRepository
import javax.inject.Inject

class DayRepositoryImpl @Inject constructor(
    private val dayRepository: DayRepository
) {
    suspend fun insertDay(day: Day) = dayRepository.insertDay(day)

    suspend fun getDayByDate(date: String) = dayRepository.getDayByDate(date)

    suspend fun getAllDay(): List<Day> = dayRepository.getAllDay()

    suspend fun changePriority(newPriority: List<ToDo>, date: String) = dayRepository.changePriorityToDos(newPriority, date)

    suspend fun changeToDos(newToDos: List<ToDo>, date: String) = dayRepository.changeToDos(newToDos, date)

    suspend fun changeEmotion(newEmotion: String, date: String) = dayRepository.changeEmotion(newEmotion, date)
}