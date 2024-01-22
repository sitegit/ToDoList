package com.example.todolist.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todolist.data.local.model.ToDoDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM to_do_list WHERE start_time >= :dayStart AND finish_time <= :dayEnd")
    suspend fun getTasksForDay(dayStart: Long, dayEnd: Long): List<ToDoDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: ToDoDb)

    @Query("SELECT * FROM to_do_list WHERE id = :id")
    suspend fun getTask(id: Int): ToDoDb
}