package com.example.todo.domain.usecase

import com.example.todo.data.local.model.ListTask
import com.example.todo.data.local.repositoryImpl.ListTaskRepositoryImpl
import javax.inject.Inject

class InsertListTaskUseCase @Inject constructor(
    private val listTaskRepositoryImpl: ListTaskRepositoryImpl
) {
    suspend fun invoke(listTask: ListTask) = listTaskRepositoryImpl.insertListTask(listTask)
}