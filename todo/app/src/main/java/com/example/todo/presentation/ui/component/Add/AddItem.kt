package com.example.todo.presentation.ui.component.Add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.presentation.Settings.SettingsViewModel
import com.example.todo.presentation.screens.Calendar.CalendarViewModel
import com.example.todo.presentation.screens.Main.MainViewModel
import com.example.todo.presentation.ui.component.ListItem.ListItemViewModel
import com.example.todo.presentation.ui.theme.ColorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItem(
    mainViewModel: MainViewModel? = null,
    calendarViewModel: CalendarViewModel? = null,
    listItemViewModel: ListItemViewModel? = null,
    settingsViewModel: SettingsViewModel
) {
    val state = settingsViewModel.stateSettings.collectAsState()
    val colorTheme = ColorTheme(state.value.theme)
    val viewModel = AddViewModel()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .background(colorTheme.Background),
        horizontalArrangement = Arrangement.spacedBy(5.dp)

    ) {
        Card(
            modifier = Modifier
                .padding(start = 5.dp, end = 20.dp, bottom = 5.dp, top = 5.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            TextField(
                value = viewModel.title, {
                    viewModel.setText(it)
                },
                placeholder = { Text(text = "Введите задачу...") },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                ),
                textStyle = TextStyle(fontSize = 18.sp),
                singleLine = true,
                modifier = Modifier
                    .width(280.dp)
                    .heightIn(50.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.done),
            contentDescription = "",
            colorFilter = if (state.value.theme in listOf(
                    0,
                    1,
                    3
                )
            ) ColorFilter.tint(Color.Black) else ColorFilter.tint(Color.White),
            modifier = Modifier
                .clickable {
                    mainViewModel?.changeIsAdd()
                    mainViewModel?.addToDo(viewModel.title)
                    calendarViewModel?.changeIsAdd()
                    calendarViewModel?.addToDo(viewModel.title)
                    listItemViewModel?.changeIsAdd()
                    listItemViewModel?.addToDo(viewModel.title)
                }
                .padding(5.dp)
                .size(65.dp)
        )

    }
}