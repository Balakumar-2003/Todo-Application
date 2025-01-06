package com.balakumar.todo.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.balakumar.todo.presentation.screens.SearchScreen
import com.balakumar.todo.presentation.screens.TaskAddScreen
import com.balakumar.todo.presentation.screens.TaskDetailScreen
import com.balakumar.todo.presentation.screens.TaskEditScreen
import com.balakumar.todo.presentation.screens.TaskList
import com.balakumar.todo.presentation.viewmodel.TaskViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController,viewModel: TaskViewModel,innerPadding:PaddingValues){

    NavHost(navController = navController, startDestination = "taskListScreen"){

        composable("taskListScreen"){
            TaskList(viewModel,innerPadding,navController)
        }
        composable("taskAddScreen"){
//            val data= it.arguments?.getString("task")?:""
//            val json = Json { serializersModule= SerializerModule
//                prettyPrint=true}
//            val taskDetails =json.decodeFromString(TaskDetails.serializer(),data)
            TaskAddScreen(viewModel,navController, innerpadding = innerPadding)
        }
        composable("TaskDetailScreen/{id}"){
                val data=it.arguments?.getString("id")?:"0"
                val id=data.toInt()
                TaskDetailScreen(viewModel,id,innerPadding)
        }
        composable("TaskEditScreen/{id}"){
            val data=it.arguments?.getString("id")?:"0"
            val id=data.toInt()
            TaskEditScreen(viewModel,navController,innerPadding,id)
        }
        composable("SearchScreen"){
            SearchScreen(navController,viewModel,innerPadding)
        }
    }
}