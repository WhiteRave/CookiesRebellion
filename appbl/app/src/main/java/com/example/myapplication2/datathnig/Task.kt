package com.example.myapplication2.datathnig

import androidx.room.*

@Entity
data class Task(
    @PrimaryKey val id: Int,
    val name: String
)

@Dao
interface TaskDao {
    @Query("DELETE FROM task")
    fun deleteAll()



    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert
    fun insertAll(vararg tasks: Task)

    @Delete
    fun delete(task: Task)
}

