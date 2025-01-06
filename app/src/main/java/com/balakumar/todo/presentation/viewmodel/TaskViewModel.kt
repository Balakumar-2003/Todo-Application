package com.balakumar.todo.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balakumar.todo.data.TaskDetails
import com.balakumar.todo.presentation.repository.TaskRepository
import kotlinx.coroutines.launch
import java.util.Date

class TaskViewModel(val repository: TaskRepository): ViewModel() {
    private var _taskList:MutableLiveData<List<TaskDetails>> =MutableLiveData<List<TaskDetails>>( emptyList())
    var isLoading:MutableState<Boolean> = mutableStateOf(true)
    var taskList:MutableLiveData<List<TaskDetails>> = _taskList
    var emptyTask:MutableState<TaskDetails?> = mutableStateOf(null )
    var deleteTask:MutableState<TaskDetails?> = mutableStateOf(null )
    var clicked:MutableLiveData<Int> = MutableLiveData(0)
    var updateTask:MutableState<TaskDetails?> = mutableStateOf(null )


    init {
        viewModelScope.launch {
            getTask()
        }

    }
    fun clickedFilter(click:Int){
        clicked.value=click
    }
    suspend fun getTask(){
        _taskList.value = repository.getAllTasks()
        isLoading.value=false
    }

    suspend fun insertTask(){
        emptyTask.value?.let {
            repository.insertTask(it)
        }
        getTask()
        emptyTask.value=null

    }
    suspend fun deleteTask(){
        deleteTask.value?.let {
            repository.deleteTask(it)
        }
        getTask()
        deleteTask.value=null
    }
    suspend fun updateStatus(status:Int,id:Int){
        repository.updateStatus(status,id)
        getTask()
    }
    fun getTaskbyId(taskId:Int):TaskDetails{
        val task= _taskList.value?.filter { it.id==taskId }
        return task?.get(0) ?: TaskDetails(id=0,0,"","","", Date(), Date(),Date())
    }
    suspend fun updateTask(){
        updateTask.value?.let{
            repository.updateTask(it)
        }
        getTask()
        updateTask.value=null
    }
    suspend fun getTaskbyName(name:String): List<TaskDetails>? {
        return _taskList.value?.filter {
            getCheck(name,it.taskName)
        }
    }
    private suspend fun getCheck(name:String,taskName:String):Boolean{
        if(taskName.equals(name)){
            return true
        }
        var st=""
        for(i in 0..taskName.length-1){
            if(st.equals(name)){
                return true
            }
            else{
                st+=taskName[i]
            }
        }
        return false
    }

}