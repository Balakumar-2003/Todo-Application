package com.balakumar.todo.presentation.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.balakumar.todo.R
import com.balakumar.todo.data.TaskDetails
import com.balakumar.todo.presentation.ui.theme.TodoTheme
import com.balakumar.todo.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TaskCard(navController: NavHostController,viewModel: TaskViewModel,task:TaskDetails,onDelete:()->Unit,clicked:Int){
    val scope= rememberCoroutineScope()
    var checked by remember { mutableStateOf(false) }
    var offsetx by remember { mutableStateOf(0f) }
    var maxOffset =100f
    val animatedOffsetx by animateDpAsState(targetValue = offsetx.dp)
    Surface( modifier = Modifier
        .fillMaxWidth().height(72.dp).pointerInput(Unit){
            detectTapGestures(
                onTap = {navController.navigate("TaskDetailScreen/${task.id}")},
                onDoubleTap = {navController.navigate("TaskEditScreen/${task.id}")}
            )
        }
        ) {
        Surface(
            modifier = Modifier
                .height(72.dp)
                .padding(2.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxHeight()
                    .width(maxOffset.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    modifier = Modifier.clickable{onDelete()},
                    text = "Delete",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

        }
        Surface(
            modifier = Modifier
                .fillMaxWidth().height(72.dp).offset(x=animatedOffsetx)
                .padding(2.dp).pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            offsetx=(offsetx + dragAmount).coerceIn(-maxOffset, 0f)
                        },
                        onDragEnd = {
                            if (offsetx <= -maxOffset / 2) {
                                offsetx=-maxOffset
                            } else {
                                offsetx=0f
                            }
                        },
                        onDragCancel = {
                            offsetx=0f
                        }
                    )

                },
            shape = RoundedCornerShape(25),
            color = Color(0xFFEAF2FF)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {checked=true
                                     if(checked) {
                                         task.status = 2
                                         scope.launch {
                                             viewModel.updateStatus(task.status, task.id ?: 0)
                                         }
                                     }
                                     }, modifier = Modifier.padding(0.dp)) {
                    Box(modifier = Modifier.padding(2.dp), contentAlignment = Alignment.Center){
                        Icon(
                            painter = painterResource(R.drawable.baseline_check_box_outline_blank_24),
                            contentDescription = "check box",
                            modifier = Modifier.size(25.dp),
                            tint = Color(0xFFB4DBFF)
                        )
                        if(checked || task.status==2){
                            Icon(imageVector = Icons.Default.Check,
                                contentDescription = "clicked",
                                modifier = Modifier.size(25.dp),
                                tint = Color(0xFFB4DBFF))
                        }

                    }

                }
//                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.width(240.dp).fillMaxHeight()
                        .padding(2.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = task.taskName,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.headlineLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = task.label, fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column(
                    modifier = Modifier.width(30.dp).fillMaxHeight()
                        .padding(2.dp).weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    if(clicked==0) {
                        Text(
                            text = formatter.format(task.startDate),
                            style = MaterialTheme.typography.headlineLarge,
                            maxLines = 1,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatter.format(task.endDate),
                        style = MaterialTheme.typography.headlineLarge,
                        maxLines = 1,
                    )
                }


            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskCard(){
    TodoTheme {
        val viewmodel:TaskViewModel= viewModel()
        TaskCard(navController = rememberNavController(), viewmodel,
            TaskDetails(
                id = 1,
                taskName = "task-1read more books that are very usefull",
                label = "read more books that are very usefull",
                description = "read book daily",
                createdDate = Date(),
                startDate = Date(),
                endDate = Date()
            ),
            onDelete = {},0
        )
    }
}