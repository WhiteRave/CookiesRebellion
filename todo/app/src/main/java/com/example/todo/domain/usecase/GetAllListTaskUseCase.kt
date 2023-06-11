package com.example.todo.domain.usecase

import com.example.todo.data.local.model.ListTask
import com.example.todo.data.local.repositoryImpl.ListTaskRepositoryImpl
import javax.inject.Inject

class GetAllListTaskUseCase @Inject constructor(
    private val listTaskRepositoryImpl: ListTaskRepositoryImpl
) {
    suspend fun invoke(): List<ListTask> = listTaskRepositoryImpl.getAllListTask()
}