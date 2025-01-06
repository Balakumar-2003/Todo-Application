package com.balakumar.todo.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.balakumar.todo.data.TaskDataBase
import com.balakumar.todo.presentation.repository.TaskRepository
import com.balakumar.todo.presentation.screens.TaskListScreen
import com.balakumar.todo.presentation.ui.theme.TodoTheme
import com.balakumar.todo.presentation.viewmodel.TaskViewModel
import com.balakumar.todo.presentation.viewmodel.TaskViewModelFactory
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    lateinit var viewModel: TaskViewModel
    lateinit var navController: NavHostController
    @OptIn(ExperimentalAnimationApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                val context = LocalContext.current
                val taskDao = TaskDataBase.getDataBase(context.applicationContext)?.getTaskDao()
                val taskRepository = taskDao?.let { TaskRepository(it) }
                navController= rememberAnimatedNavController()
                viewModel =viewModel(factory = taskRepository?.let { TaskViewModelFactory(it) })
                TaskListScreen(viewModel,navController)
            }
        }
    }
}

