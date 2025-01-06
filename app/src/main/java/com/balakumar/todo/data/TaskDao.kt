package com.balakumar.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskDetails")
    suspend fun getAllTasks():List<TaskDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskDetails: TaskDetails)

    @Delete
    suspend fun deleteTask(taskDetails: TaskDetails)

    @Update
    suspend fun updateTask(taskDetails: TaskDetails)

    @Query("UPDATE TaskDetails SET status=:status WHERE id=:id")
    suspend fun updateStatus(status:Int,id:Int)


}