package com.balakumar.todo.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.balakumar.todo.presentation.viewmodel.TaskViewModel

@Composable
fun TaskDetailScreen(viewModel: TaskViewModel,id:Int,innerPadding:PaddingValues){
    val task =viewModel.getTaskbyId(id)
    val startDate = task.startDate.toString()
    val endDate = task.endDate.toString()
    val createDate = task.createdDate.toString()
    Column (modifier = Modifier.fillMaxSize().padding(innerPadding).padding(8.dp).verticalScroll(
        rememberScrollState()
    ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top){
        Text(text="Task Name:",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = Color(0xFF006FFD),
            style = MaterialTheme.typography.headlineLarge)
        Text(text=task.taskName,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp,8.dp),
            style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Task Label",
            fontSize = 18.sp,
            lineHeight = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = Color(0xFF006FFD),
            style = MaterialTheme.typography.headlineLarge)
        Text(text = task.label,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp,8.dp),
            style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "description",
            fontSize = 16.sp,
            lineHeight = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            color = Color(0xFF006FFD),
            style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = task.description,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp,8.dp),
            style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF006FFD))){
                append("Start Date :")
            }
            append(startDate)
        },
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF006FFD))){
                append("End Date :")
            }
            append(endDate)
        },
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF006FFD))){
                append("Created Date :")
            }
            append(createDate)
        },
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.headlineMedium)



    }

}