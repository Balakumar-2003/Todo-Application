package com.balakumar.todo.presentation.repository

import com.balakumar.todo.data.TaskDao
import com.balakumar.todo.data.TaskDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository (val taskDao: TaskDao){
    private var taskList:List<TaskDetails> = emptyList()

    suspend fun getAllTasks():List<TaskDetails> = withContext(Dispatchers.IO){
        if(taskList.isNotEmpty()){
            return@withContext taskList
        }
        else{
            taskList = taskDao.getAllTasks()
            return@withContext taskList
        }

    }
    suspend fun insertTask(task:TaskDetails)= withContext(Dispatchers.IO){
        taskDao.insertTask(task)
        taskList=taskDao.getAllTasks()
    }
    suspend fun  deleteTask(task: TaskDetails)= withContext(Dispatchers.IO){
        taskDao.deleteTask(task)
        taskList=taskDao.getAllTasks()
    }
    suspend fun  updateTask(task: TaskDetails)= withContext(Dispatchers.IO){
        taskDao.updateTask(task)
        taskList=taskDao.getAllTasks()
    }
    suspend fun updateStatus(status:Int,id:Int)= withContext(Dispatchers.IO){
        taskDao.updateStatus(status,id)
        taskList=taskDao.getAllTasks()
    }
}