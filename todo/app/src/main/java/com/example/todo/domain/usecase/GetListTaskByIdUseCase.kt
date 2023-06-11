package com.example.todo.domain.usecase

import com.example.todo.data.local.model.ListTask
import com.example.todo.data.local.repositoryImpl.ListTaskRepositoryImpl
import javax.inject.Inject

class GetListTaskByIdUseCase @Inject constructor(
    private val listTaskRepositoryImpl: ListTaskRepositoryImpl
){
    suspend fun invoke(id: Long): ListTask = listTaskRepositoryImpl.getListTaskById(id)
}