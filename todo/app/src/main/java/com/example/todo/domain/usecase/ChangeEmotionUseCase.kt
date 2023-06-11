package com.example.todo.domain.usecase

import com.example.todo.data.local.repositoryImpl.DayRepositoryImpl
import javax.inject.Inject

class ChangeEmotionUseCase @Inject constructor(
    private val dayRepositoryImpl: DayRepositoryImpl
) {
    suspend fun invoke(newEmotion: String, date: String) = dayRepositoryImpl.changeEmotion(newEmotion, date)
}