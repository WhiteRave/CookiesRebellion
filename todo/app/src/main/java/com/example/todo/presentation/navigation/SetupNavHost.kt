package com.example.todo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.R
import com.example.todo.presentation.Settings.SettingsScreen
import com.example.todo.presentation.Settings.SettingsViewModel
import com.example.todo.presentation.screens.Calendar.CalendarScreen
import com.example.todo.presentation.screens.Emotion.EmotionScreen
import com.example.todo.presentation.screens.Greeting.GreetingScreen
import com.example.todo.presentation.screens.List.ListScreen
import com.example.todo.presentation.screens.Main.MainScreen
import com.example.todo.presentation.ui.component.ListItem.ListItemToDo


sealed class Screens(val rout: String, val icon: Int) {
    object Greeting : Screens(rout = "greeting_screen", R.drawable.star)

    object Main : Screens(rout = "main_screen", R.drawable.main)
    object Calendar : Screens(rout = "calendar_screen", R.drawable.calendar)
    object Emotion : Screens(rout = "emotion_screen", R.drawable.emotion)
    object Editor : Screens(rout = "editor_screen", R.drawable.editor)
    object SettingsScreen : Screens(rout = "settings_screen", R.drawable.settings)
    object ListItem : Screens(rout = "listItem_screen/{id}", R.drawable.phone)
}

@Composable
fun SetupNavHost(navController: NavHostController, viewModel: NavigationViewModel) {
    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    NavHost(
        navController = navController,
        startDestination = if (viewModel.days.isEmpty()) Screens.Greeting.rout else Screens.Main.rout
    ) {
        composable(route = Screens.Greeting.rout) {
            GreetingScreen(navController, viewModel)
        }
        composable(route = Screens.Main.rout) {
            MainScreen(navController, settingsViewModel)
        }
        composable(route = Screens.Calendar.rout) {
            CalendarScreen(navController = navController, settingsViewModel)
        }
        composable(route = Screens.Emotion.rout) {
            EmotionScreen(navController = navController, settingsViewModel)
        }
        composable(route = Screens.Editor.rout) {
            ListScreen(navController = navController, settingsViewModel)
        }
        composable(route = Screens.ListItem.rout) {
            val id = it.arguments?.getString("id")?.toLong()
            ListItemToDo(navController = navController, id, settingsViewModel)
        }

        composable(route = Screens.SettingsScreen.rout) {
            SettingsScreen(settingsViewModel)
        }

    }
}