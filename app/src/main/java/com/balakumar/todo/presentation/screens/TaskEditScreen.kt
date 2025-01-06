package com.balakumar.todo.presentation.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.balakumar.todo.R
import com.balakumar.todo.data.TaskDetails
import com.balakumar.todo.presentation.viewmodel.TaskViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskEditScreen(viewModel: TaskViewModel, navController: NavHostController, innerpadding: PaddingValues,id:Int){
    val scope= rememberCoroutineScope()
    val newTask=viewModel.getTaskbyId(id)
    val focusmanager = LocalFocusManager.current
    val context = LocalContext.current
    var taskName by remember { mutableStateOf(newTask.taskName) }
    var taskLabel by remember { mutableStateOf(newTask.label) }
    val simpleFormatter = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date= LocalDate.now()
    var create by remember { mutableStateOf(simpleFormatter.format(newTask.createdDate)) }

    var taskDescription by remember { mutableStateOf(newTask.description) }
    var taskStartDate by remember { mutableStateOf(simpleFormatter.format(newTask.startDate)) }
    var taskEndDate by remember { mutableStateOf(simpleFormatter.format(newTask.endDate)) }
    val startdateState = rememberSheetState()


    CalendarDialog(state = startdateState, selection = CalendarSelection.Date{ date ->
        try {
            taskStartDate =date.format(formatter)
        }
        catch (e:Exception){
            Toast.makeText(context,e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    })
    val enddateState = rememberSheetState()
    CalendarDialog(state = enddateState, selection = CalendarSelection.Date{ date ->
        try {
            taskEndDate = date.format(formatter)
        }
        catch (e:Exception){
            Toast.makeText(context,e.message.toString(), Toast.LENGTH_SHORT).show()
        }

    })
    Column (modifier = Modifier.fillMaxSize().padding(innerpadding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top){
        Text(text = "Task Name",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.headlineLarge)
        OutlinedTextField(value=taskName, onValueChange = { taskName=it}, modifier = Modifier.fillMaxWidth()
            .height(70.dp).padding(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusmanager.moveFocus(FocusDirection.Down)}
            ),
            colors = TextFieldDefaults
                .outlinedTextFieldColors(unfocusedBorderColor =
                Color(0xFF000000),
                    containerColor = Color(0xFFB4DBFF)
                ),
            singleLine = true)
        Text(text = "Label",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.headlineLarge)
        OutlinedTextField(value=taskLabel, onValueChange = {
            taskLabel=it}, modifier = Modifier.fillMaxWidth()
            .height(70.dp).padding(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusmanager.moveFocus(FocusDirection.Down)}
            ),
            colors = TextFieldDefaults
                .outlinedTextFieldColors(unfocusedBorderColor =
                Color(0xFF000000),
                    containerColor = Color(0xFFB4DBFF)
                ),
            singleLine = true, maxLines = 1)
        Text(text = "Description",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.headlineLarge)
        OutlinedTextField(value=taskDescription, onValueChange = {
            taskDescription=it}, modifier = Modifier.fillMaxWidth()
            .height(70.dp).padding(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusmanager.moveFocus(FocusDirection.Down)}
            ),
            colors = TextFieldDefaults
                .outlinedTextFieldColors(unfocusedBorderColor =
                Color(0xFF000000),
                    containerColor = Color(0xFFB4DBFF)
                ),
            singleLine = true, maxLines = 1)
        Text(text = "Date",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.headlineLarge)
        Row (modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom){
            TextField(value = taskStartDate, onValueChange = {taskStartDate=it},
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {focusmanager.moveFocus(FocusDirection.Down)}
                ),
                label = { Text("start date (yyyy-mm-dd)") },
                modifier = Modifier.padding(top=16.dp,bottom=4.dp),
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color.Transparent))
            IconButton(onClick = {startdateState.show()}){
                Icon(painter = painterResource(R.drawable.baseline_edit_calendar_24),
                    modifier = Modifier.size(40.dp),
                    contentDescription = "Calendar",
                    tint = Color(0xFFB4DBFF)
                )
            }

        }
        Row (modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom){
            TextField(value = taskEndDate, onValueChange = {taskEndDate=it},
                label = { Text("end date(yyyy-mm-dd)") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusmanager.clearFocus()
                        if(taskName==""){
                            Toast.makeText(context,"task name is empty", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            newTask.taskName=taskName
                            newTask.label=taskLabel
                            newTask.description=taskDescription
                            newTask.startDate
                            try {
                                newTask.startDate = simpleFormatter.parse(taskStartDate)?: throw IllegalArgumentException("Invalid date format")
                            }
                            catch (e:Exception){
                                Toast.makeText(context,"Invalid start date format", Toast.LENGTH_SHORT).show()
                            }
                            try {
                                newTask.endDate = simpleFormatter.parse(taskEndDate)?: throw IllegalArgumentException("Invalid date format")
                            }
                            catch (e:Exception){
                                Toast.makeText(context,"Invalid end date format", Toast.LENGTH_SHORT).show()
                            }
                            val localDate= LocalDate.now()
                            create=localDate.format(formatter)
                            try{
                                newTask.createdDate=simpleFormatter.parse(create)?:throw IllegalArgumentException("Invalid create date")
                            }
                            catch (e:Exception){
                                Toast.makeText(context,"Invalid create date format", Toast.LENGTH_SHORT).show()
                            }
                            if(newTask.startDate==newTask.createdDate){
                                newTask.status=1
                            }
                            viewModel.updateTask.value=newTask
                            scope.launch {
                                viewModel.updateTask()
                            }
                            navController.navigate("taskListScreen")
                        }

                    }
                ),
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color.Transparent))

            IconButton(onClick = {enddateState.show()}){
                Icon(painter = painterResource(R.drawable.baseline_edit_calendar_24),
                    modifier = Modifier.size(40.dp),
                    contentDescription = "Calendar",
                    tint = Color(0xFFB4DBFF)
                )
            }

        }
        Spacer(modifier = Modifier.height(36.dp))
        Box(modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(2.dp),
            contentAlignment = Alignment.Center){
            Button(onClick = {
                if(taskName==""){
                    Toast.makeText(context,"task name is empty", Toast.LENGTH_SHORT).show()
                }
                else{
                    newTask.taskName=taskName
                    newTask.label=taskLabel
                    newTask.description=taskDescription
                    newTask.startDate
                    try {
                        newTask.startDate = simpleFormatter.parse(taskStartDate)?: throw IllegalArgumentException("Invalid date format")
                    }
                    catch (e:Exception){
                        Toast.makeText(context,"Invalid start date format", Toast.LENGTH_SHORT).show()
                    }
                    try {
                        newTask.endDate = simpleFormatter.parse(taskEndDate)?: throw IllegalArgumentException("Invalid date format")
                    }
                    catch (e:Exception){
                        Toast.makeText(context,"Invalid end date format", Toast.LENGTH_SHORT).show()
                    }
                    val localDate= LocalDate.now()
                    create=localDate.format(formatter)
                    try{
                        newTask.createdDate=simpleFormatter.parse(create)?:throw IllegalArgumentException("Invalid create date")
                    }
                    catch (e:Exception){
                        Toast.makeText(context,"Invalid create date format", Toast.LENGTH_SHORT).show()
                    }
                    if(newTask.startDate==newTask.createdDate){
                        newTask.status=1
                    }
                    viewModel.updateTask.value=newTask
                    scope.launch {
                        viewModel.updateTask()
                    }
                    navController.navigate("taskListScreen")
                }
            }, modifier = Modifier
                .wrapContentHeight().wrapContentWidth(),
                shape= RoundedCornerShape(40),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF006FFD)
                )) {
                Text("Edit project",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 14.sp)
            }
        }

    }
}