package com.balakumar.todo.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.balakumar.todo.R
import com.balakumar.todo.data.TaskDetails
import com.balakumar.todo.presentation.ui.theme.TodoTheme
import com.balakumar.todo.presentation.navigation.Navigation
import com.balakumar.todo.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(viewModel: TaskViewModel,navController:NavHostController){
    var screenRoute by remember { mutableStateOf("") }
    var taskName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val scope= rememberCoroutineScope()
    navController.addOnDestinationChangedListener{listener,
        destination,argument->
        screenRoute=destination.route?:""

    }
    Scaffold (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        topBar = { TopAppBar(
            colors=TopAppBarDefaults
                .topAppBarColors(Color(0xFFB4DBFF)),
            title = {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                    contentAlignment = Alignment.Center){
                    if(screenRoute=="taskAddScreen"){
                        Text("Add new project",
                            style = MaterialTheme.typography.headlineLarge)
                    }
                    else if(screenRoute == "SearchScreen"){
                        Text("Search projects",
                            style = MaterialTheme.typography.headlineLarge)
                    }
                    else if(screenRoute!="taskListScreen"){
                        Text("Project details",
                            style = MaterialTheme.typography.headlineLarge)
                    }
                    else{
                        Text("Projects",
                            style = MaterialTheme.typography.headlineLarge)
                    }
                }
            },
            actions = {
                if(screenRoute == "taskListScreen"){
                    IconButton(onClick = { navController.navigate("SearchScreen") }) {
                        Icon(imageVector = Icons.Default.Search,
                            contentDescription = "search icon",
                            tint = Color(0xFF006FFD))
                    }
                }

            },
            navigationIcon = {
                if (screenRoute!="taskListScreen"){
                    IconButton(onClick = {navController.popBackStack()}){
                        Icon(painter = painterResource(R.drawable.back),
                            contentDescription = "back button",
                            tint =Color(0xFF006FFD) )
                    }
                }

            })
        },
        bottomBar = { if(screenRoute=="taskListScreen"){BottomAppBar (modifier = Modifier.fillMaxWidth().height(128.dp), containerColor = Color.Transparent){
            Surface (modifier = Modifier.fillMaxWidth()
                .height(80.dp)
                .padding(start = 16.dp,end=16.dp, bottom = 16.dp).background(Color.Transparent),
                shape = RoundedCornerShape(25), shadowElevation = 2.dp,
                color = Color(0xFFF8F9FE)){
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top) {
                    Text("Add a Quick Task", modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.headlineLarge)
                    TextField(value = taskName, onValueChange = {taskName=it},
                        modifier = Modifier.fillMaxWidth().heightIn(min=56.dp).padding(start = 4.dp),
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                        placeholder = { Text("task name") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()
                            val newTask= TaskDetails(taskName = "", label = "", description = "", createdDate = Date(), startDate = Date(), endDate = Date())
                            if(taskName!="") {
                                newTask.taskName = taskName
                                if(newTask.startDate==newTask.createdDate){
                                    newTask.status=1
                                }

                                viewModel.emptyTask.value = newTask
                                scope.launch {
                                    viewModel.insertTask()
                                }
                            }
                            else{
                                Toast.makeText(context,"The task name is empty",Toast.LENGTH_SHORT).show()
                            }
                            taskName=""


                        })
                    )
                }
            }
        } }}
//        , floatingActionButton = {
//            IconButton(onClick = {navController.navigate("taskAddScreen")}){
//                Icon(painter = painterResource(R.drawable.add), contentDescription = "add", modifier = Modifier.size(36.dp).padding(2.dp))
//            }
//        }

    ){innerPadding->
        Navigation(navController,viewModel,innerPadding)


    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewTaskListScreen(){
    TodoTheme {
        val viewModel:TaskViewModel=viewModel()
        TaskListScreen(viewModel = viewModel, rememberNavController())
    }
}
