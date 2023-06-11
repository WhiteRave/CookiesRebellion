package com.example.todo.presentation.ui.component.ToDoRow

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.R
import com.example.todo.data.local.model.ToDo
import com.example.todo.presentation.screens.Calendar.CalendarViewModel
import com.example.todo.presentation.screens.Main.MainViewModel
import com.example.todo.presentation.ui.component.ListItem.ListItemViewModel

@Composable
fun ToDoRow(toDo: ToDo, mainViewModel: MainViewModel? = null, calendarViewModel: CalendarViewModel? = null, listItemViewModel: ListItemViewModel? = null) {
    val viewModel = ToDoRowModel()
    viewModel.initToDo(toDo)
    val toDoState by viewModel.stateToDo.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    mainViewModel?.let { viewModel.changeIsDone(mainViewModel = it) }
                    calendarViewModel?.let { viewModel.changeIsDone(calendarViewModel = it) }
                    listItemViewModel?.let { viewModel.changeIsDone(listItemViewModel = it) }
                },
            painter = painterResource(id = if (toDoState.done) R.drawable.done else R.drawable.notdone),
            contentDescription = "isDone"
        )
        Text(
            modifier = Modifier
                .width(150.dp)
                .clickable {
                    viewModel.changeIsPriority(
                        mainViewModel = mainViewModel,
                        calendarViewModel = calendarViewModel,
                        listItemViewModel = listItemViewModel
                    )
                },
            text = toDoState.title,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )

        if (toDoState.priority) {
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "Избранное",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(25.dp)
                    .clickable {
                        viewModel.changeIsPriority(
                            mainViewModel = mainViewModel,
                            calendarViewModel = calendarViewModel,
                            listItemViewModel = listItemViewModel
                        )
                    }
            )
        }

        Icon(
            imageVector = Icons.Filled.Delete,
            tint = Color.White,
            contentDescription = "add",
            modifier = Modifier.padding(start = 10.dp).size(25.dp).clickable {
                calendarViewModel?.deleteToDo(toDo)
                listItemViewModel?.deleteToDo(toDo)
                mainViewModel?.deleteToDo(toDo)
            }
        )
    }
}