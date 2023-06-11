package com.example.todo.presentation.screens.Main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
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
import com.example.todo.presentation.Settings.SettingsViewModel
import com.example.todo.presentation.ui.component.Add.AddItem
import com.example.todo.presentation.ui.component.ToDoRow.ToDoRow
import com.example.todo.presentation.ui.theme.ColorTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController, settingsViewModel: SettingsViewModel) {
    val state = settingsViewModel.stateSettings.collectAsState()
    val colorTheme = ColorTheme(state.value.theme)
    val viewModel = hiltViewModel<MainViewModel>()
    viewModel.initDay()
    val dayState by viewModel.dayState.collectAsState()
    val toDos = viewModel.toDos.observeAsState(listOf()).value
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
                    imageVector = Icons.Filled.Add,
                    tint = Color.White,
                    contentDescription = "add"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .background(colorTheme.Background)
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 60.dp, end = 60.dp),
                text = "Мотивация на этот день:",
                textAlign = TextAlign.Center,
                color = colorTheme.TextColorWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                text = dayState.motivation,
                color = colorTheme.TextColorWhite,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
            if (viewModel.isAdd) {
                AddItem(mainViewModel = viewModel, settingsViewModel = settingsViewModel)
            }
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
                    text = "Весь список дел на сегодня",
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
                            ToDoRow(toDo = toDo, mainViewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}