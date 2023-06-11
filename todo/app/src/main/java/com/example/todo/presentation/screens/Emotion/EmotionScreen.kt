package com.example.todo.presentation.screens.Emotion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todo.domain.usecase.GetCurrentDateUseCase
import com.example.todo.presentation.Settings.SettingsViewModel
import com.example.todo.presentation.ui.component.EmotionItem.AddItem
import com.example.todo.presentation.ui.component.EmotionItem.EmotionItem
import com.example.todo.presentation.ui.theme.ColorTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionScreen(navController: NavController, settingsViewModel: SettingsViewModel) {
    val state = settingsViewModel.stateSettings.collectAsState()
    val colorTheme = ColorTheme(state.value.theme)
    val viewModel = hiltViewModel<EmotionViewModel>()
    val currentDate = GetCurrentDateUseCase().invoke()
    val days = viewModel.days.observeAsState(listOf()).value
    val calendar = Calendar.getInstance()
    val localFocusManager = LocalFocusManager.current
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    if (viewModel.isAdd) {
        AddItem(viewModel = viewModel, settingsViewModel)
    } else {
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
                    .background(colorTheme.Background),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp),
                    text = "Трекер настроения",
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center,
                    color = colorTheme.TextColorWhite,
                    fontWeight = FontWeight.Bold,
                )
                LazyColumn(modifier = Modifier.border(width = 1.dp, color = Color.Black)) {
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    itemsIndexed(
                        if (
                        days.isNotEmpty() && days[0].emotion == ""
                        )
                        days.drop(1).filter { viewModel.compareDates(it.date, currentDate) <= 0 }.sortedBy { LocalDate.parse(it.date, formatter) }
                    else
                        days.filter { viewModel.compareDates(it.date, currentDate) <= 0 }.sortedBy { LocalDate.parse(it.date, formatter) }
                    )
                    { _, item ->
                        EmotionItem(item = item, viewModel = viewModel, settingsViewModel)
                    }
                }
            }
        }
    }
}