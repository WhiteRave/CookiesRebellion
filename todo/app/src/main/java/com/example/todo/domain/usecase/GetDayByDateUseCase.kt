package com.example.todo.domain.usecase

import com.example.todo.data.local.model.Day
import com.example.todo.data.local.repositoryImpl.DayRepositoryImpl
import javax.inject.Inject

class GetDayByDateUseCase @Inject constructor(
    private val dayRepositoryImpl: DayRepositoryImpl
) {
    suspend fun invoke(date: String): Day = dayRepositoryImpl.getDayByDate(date)

}