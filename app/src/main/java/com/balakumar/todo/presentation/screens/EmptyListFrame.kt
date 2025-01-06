package com.balakumar.todo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.balakumar.todo.R
import com.balakumar.todo.presentation.ui.theme.TodoTheme


@Composable
fun EmptyListFrame(navController: NavHostController){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Surface (modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .padding(8.dp),
            shape = RoundedCornerShape(20)){
            Image(painter = painterResource(id = R.drawable.taskimage),
                contentDescription = "taskImage",
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(4.dp),
            contentAlignment = Alignment.Center){
            Column (modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top){
                Text(modifier = Modifier.padding(8.dp), text = "Nothing here.For now.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineLarge)
                Text(text = "This is where you'll find your ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin,
                    style = MaterialTheme.typography.headlineSmall, color = Color(0xFF71727A))
                Text(text = "finished projects",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Thin,
                    style = MaterialTheme.typography.headlineSmall, color = Color(0xFF71727A))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("taskAddScreen") }, modifier = Modifier.wrapContentHeight().wrapContentWidth(), shape= RoundedCornerShape(40), colors = ButtonDefaults.buttonColors(
            Color(0xFF006FFD)
        )) {
            Text("Start a project", style = MaterialTheme.typography.headlineMedium, fontSize = 14.sp)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyListScreen(){
    TodoTheme {
        EmptyListFrame(rememberNavController())
    }
}