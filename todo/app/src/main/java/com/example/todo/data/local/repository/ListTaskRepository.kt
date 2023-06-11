package com.example.todo.data.local.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.data.local.model.ListTask
import com.example.todo.data.local.model.ToDo

@Dao
interface ListTaskRepository {

    @Insert
    suspend fun insertListTask(listTask: ListTask)

    @Query("select * from listtask")
    suspend fun getAllListTask(): List<ListTask>

    @Query("UPDATE listtask SET toDos=:newToDos WHERE id=:id")
    suspend fun changeToDos(newToDos: List<ToDo>, id: Long)

    @Query("select * from listtask WHERE id=:id")
    suspend fun getListTaskById(id: Long): ListTask

    @Query("delete from listtask where id=:id")
    suspend fun deleteTask(id: Long)
}