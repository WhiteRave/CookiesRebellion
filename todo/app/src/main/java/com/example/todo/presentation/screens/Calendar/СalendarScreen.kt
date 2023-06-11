package com.example.todo.presentation.screens.Calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todo.presentation.Settings.SettingsViewModel
import com.example.todo.presentation.ui.component.Add.AddItem
import com.example.todo.presentation.ui.component.ToDoRow.ToDoRow
import com.example.todo.presentation.ui.theme.ColorTheme
import java.util.Calendar
import java.util.Locale
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(navController: NavController, settingsViewModel: SettingsViewModel) {
    val state = settingsViewModel.stateSettings.collectAsState()
    val colorTheme = ColorTheme(state.value.theme)
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val viewModel = hiltViewModel<CalendarViewModel>()
    val localFocusManager = LocalFocusManager.current
    val toDos = viewModel.toDos.observeAsState(listOf()).value
    if (viewModel.day == 0) {
        viewModel.day = calendar.get(Calendar.DAY_OF_MONTH)
        viewModel.currentDay(viewModel.day)
    }
    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                localFocusManager.clearFocus()
                viewModel.isAdd = false
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colorTheme.BasicBox),
                onClick = { viewModel.changeIsAdd() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = "add"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(colorTheme.Background)
        ) {
            Text(
                text = "${
                    calendar.getDisplayName(
                        Calendar.MONTH,
                        Calendar.LONG,
                        Locale.getDefault()
                    )
                } $currentYear",
                color = colorTheme.TextColorWhite,
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            val dayOfWeekLabels = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
            Row {
                for (label in dayOfWeekLabels) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .border(width = 1.dp, color = colorTheme.BorderColor)
                    ) {
                        Text(
                            text = label,
                            color = colorTheme.TextColorWhite,
                            modifier = Modifier.padding(5.dp),
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            val firstDayOfWeek = calendar.clone() as Calendar
            firstDayOfWeek.set(Calendar.DAY_OF_MONTH, 1)
            val startDayOfWeek = firstDayOfWeek.get(Calendar.DAY_OF_WEEK) - 1
            val daysBeforeMonth = if (startDayOfWeek == 0) 7 else startDayOfWeek
            val totalDays = daysInMonth + daysBeforeMonth

            val weeksInMonth = ceil(totalDays.toFloat() / 7).toInt()
            val days = mutableListOf<Int>()
            for (day in 1..daysInMonth) {
                days.add(day)
            }

            var currentDay = 1

            for (week in 0 until weeksInMonth) {
                Row {
                    for (dayOfWeek in 0 until 7) {
                        val dayIndex = (week * 7) + dayOfWeek
                        if (dayIndex < daysBeforeMonth || dayIndex >= totalDays) {
                            // Empty cell before the first day of the month or after the last day of the month
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .border(width = 1.dp, color = colorTheme.BorderColor)
                            )
                        } else {
                            // Day cell
                            val day = days[dayIndex - daysBeforeMonth]
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .border(width = 1.dp, color = colorTheme.BorderColor)
                                    .clickable {
                                        viewModel.currentDay(dayOfMonth = day)
                                    }
                            ) {
                                Text(
                                    text = day.toString(),
                                    color = colorTheme.TextColorWhite,
                                    modifier = Modifier.padding(5.dp),
                                    textAlign = TextAlign.Start,
                                    style = TextStyle(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
            if (viewModel.isAdd) {
                AddItem(calendarViewModel = viewModel, settingsViewModel = settingsViewModel)
            } else {
                Spacer(modifier = Modifier.size(40.dp))
            }
            if (toDos.isEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(100.dp),
                    fontSize = 16.sp,
                    color = Color.Black,
                    text = "В этот день нет задач... \nНажмите +, чтобы \nсоздать задачу",
                    textAlign = TextAlign.Center
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .fillMaxWidth()
                        .heightIn(min = 220.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorTheme.BasicBox),
//                contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 10.dp),
                        text = "Весь список дел на ${viewModel.getDate(dayOfMonth = viewModel.day)}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        fontSize = 21.sp,
                    )
                    if (toDos.isEmpty()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Сейчас тут пусто",
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 7.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            itemsIndexed(
                                toDos
                            ) { _, toDo ->
                                ToDoRow(toDo = toDo, calendarViewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}