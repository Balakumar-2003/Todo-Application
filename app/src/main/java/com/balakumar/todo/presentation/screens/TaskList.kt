package com.balakumar.todo.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.balakumar.todo.data.TaskDetails
import com.balakumar.todo.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskList(viewModel: TaskViewModel,innerPadding:PaddingValues,navController:NavHostController){
    val clicked by viewModel.clicked.observeAsState(0)
    val taskList = viewModel.taskList.observeAsState(emptyList()).value.filter { it.status==clicked }
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState(pageCount = {3})

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(40),
                    color = Color(0xFFF8F9FE), shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 6.dp)
                                .weight(1f)
                                .clickable(onClick = { viewModel.clickedFilter(0) }),
                            shape = RoundedCornerShape(40),
                            color = Color(if (clicked == 0) 0xFFFFFFFF else 0xFFF8F9FE)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "ToDo",
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color(if (clicked == 0) 0xFF1F2024 else 0xFF71727A)
                                )
                            }
                        }
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp, bottom = 4.dp, start = 6.dp, end = 6.dp)
                                .weight(1f)
                                .clickable(onClick = { viewModel.clickedFilter(1) }),
                            shape = RoundedCornerShape(40),
                            color = Color(if (clicked == 1) 0xFFFFFFFF else 0xFFF8F9FE)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "In Progress",
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color(if (clicked == 1) 0xFF1F2024 else 0xFF71727A)
                                )
                            }
                        }
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp, bottom = 4.dp, start = 6.dp, end = 8.dp)
                                .weight(1f)
                                .clickable(onClick = { viewModel.clickedFilter(2) }),
                            shape = RoundedCornerShape(40),
                            color = Color(if (clicked == 2) 0xFFFFFFFF else 0xFFF8F9FE)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Finished",
                                    fontSize = 12.sp,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color(if (clicked == 2) 0xFF1F2024 else 0xFF71727A)
                                )
                            }
                        }
                    }

            }
    HorizontalPager(state = pageState) { page ->
        LaunchedEffect(key1 = pageState.currentPage) {
            if (pageState.currentPage == page) {
                viewModel.clickedFilter(pageState.currentPage)
            }
        }
            Surface(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = (
                            (pageState.currentPage - page) + pageState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }, color = Color.Transparent) {
                if (taskList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(2.dp), contentAlignment = Alignment.Center
                    ) {
                        EmptyListFrame(navController)
                    }
                } else {
                    Column (modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top){
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .weight(1f, false)
                        ) {
                            itemsIndexed(
                                when (clicked) {
                                    2 -> taskList.filter { it.status == 2 }
                                    1 -> taskList.filter { it.status == 1 }
                                    0 -> taskList.filter { it.status != 2 }
                                    else -> taskList.filter { it.status != 2 }
                                }, key = { index, item -> item.id ?: 0 }) { index, item ->
                                TaskCard(navController, viewModel, item, onDelete = {
                                    scope.launch {
                                        viewModel.deleteTask.value = item
                                        viewModel.deleteTask()
                                    }
                                }, clicked)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
//                        val json = Json { serializersModule= SerializerModule
//                        prettyPrint=true}
//                        val taskDetails =json.encodeToString(TaskDetails.serializer(),task)
                                    navController.navigate("taskAddScreen")
                                }, modifier = Modifier
                                    .wrapContentHeight()
                                    .wrapContentWidth(),
                                shape = RoundedCornerShape(40),
                                colors = ButtonDefaults.buttonColors(
                                    Color(0xFF006FFD)
                                )
                            ) {
                                Text(
                                    "Add project",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                    }
                }
            }

        }

}


