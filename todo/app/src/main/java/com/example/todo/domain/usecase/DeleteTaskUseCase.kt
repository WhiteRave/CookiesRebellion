package com.example.todo.domain.usecase

import com.example.todo.data.local.repositoryImpl.ListTaskRepositoryImpl
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val listTaskRepositoryImpl: ListTaskRepositoryImpl
) {
    suspend fun invoke(id: Long) = listTaskRepositoryImpl.deleteTask(id)
}