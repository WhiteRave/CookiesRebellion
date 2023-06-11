package com.example.todo.presentation.ui.component.EmotionItem

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
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
import com.example.todo.presentation.Settings.SettingsViewModel
import com.example.todo.presentation.navigation.Screens
import com.example.todo.presentation.screens.Emotion.EmotionViewModel
import com.example.todo.presentation.ui.theme.ColorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItem(viewModel: EmotionViewModel, settingsViewModel: SettingsViewModel) {
    val state = settingsViewModel.stateSettings.collectAsState()
    val colorTheme = ColorTheme(state.value.theme)
    val localFocusManager = LocalFocusManager.current
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
                    imageVector = Icons.Filled.Done,
                    tint = Color.White,
                    contentDescription = "done"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 150.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                text = "Какой ты сегодня?",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp,
            )
            EmotionItem(item = viewModel.days.value!![0], viewModel = viewModel, settingsViewModel)
        }
    }

}