package com.example.todo.domain.usecase

import com.example.todo.data.local.model.ToDo
import com.example.todo.data.local.repositoryImpl.DayRepositoryImpl
import javax.inject.Inject

class ChangePriorityUseCase @Inject constructor(
    private val dayRepositoryImpl: DayRepositoryImpl
){
    suspend fun invoke(newPriority: List<ToDo>, date: String) = dayRepositoryImpl.changePriority(newPriority, date)
}