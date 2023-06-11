package com.example.todo.domain.usecase

import com.example.todo.data.local.model.ToDo
import com.example.todo.data.local.repositoryImpl.ListTaskRepositoryImpl
import javax.inject.Inject

class ChangeToDosListTaskUseCase @Inject constructor(
    private val listTaskRepositoryImpl: ListTaskRepositoryImpl
) {
    suspend fun invoke(newToDos: List<ToDo>, id: Long) = listTaskRepositoryImpl.changeToDos(newToDos, id)
}