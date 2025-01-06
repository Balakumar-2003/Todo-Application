package com.balakumar.todo.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.balakumar.todo.data.TaskDetails
import com.balakumar.todo.presentation.ui.theme.TodoTheme
import com.balakumar.todo.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController,viewModel: TaskViewModel,innerPaddings:PaddingValues){
    var searchTask by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
   var taskList by remember { mutableStateOf<List<TaskDetails>>(emptyList()) }
    val scope= rememberCoroutineScope()
    Column (modifier = Modifier.padding(innerPaddings).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top){
        TextField(value = searchTask, onValueChange = {searchTask=it
            scope.launch {
                taskList =viewModel.getTaskbyName(searchTask)?: emptyList()
            }},
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(top = 2.dp, bottom = 2.dp),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    scope.launch {
                        taskList =viewModel.getTaskbyName(searchTask)?: emptyList()
                    }}
            ),
                    placeholder = { Text("Search project",
                        fontSize = 18.sp,
                        color = Color.LightGray,
                        style =MaterialTheme.typography.headlineMedium) },
            trailingIcon = { IconButton(onClick = {focusManager.clearFocus()
                scope.launch {
                    taskList =viewModel.getTaskbyName(searchTask)?: emptyList()
                }}){
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "search icon",
                    tint = Color.Black)
            } })
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            itemsIndexed(taskList, key = {index,item->item.id?:0}){index,item->
                TaskCard(navController,viewModel,item, onDelete = {scope.launch {
                    viewModel.deleteTask.value=item
                    viewModel.deleteTask()
                }},0)
            }
        }

    }
}
