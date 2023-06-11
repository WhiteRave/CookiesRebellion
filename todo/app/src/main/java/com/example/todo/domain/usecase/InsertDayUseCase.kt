package com.example.todo.domain.usecase

import com.example.todo.data.local.model.Day
import com.example.todo.data.local.repositoryImpl.DayRepositoryImpl
import javax.inject.Inject

class InsertDayUseCase @Inject constructor(
    private val dayRepositoryImpl: DayRepositoryImpl
) {
    suspend fun invoke(day: Day) = dayRepositoryImpl.insertDay(day)
}