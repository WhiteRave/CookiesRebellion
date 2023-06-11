package com.example.todo.presentation.ui.theme

import androidx.compose.ui.graphics.Color

class ColorTheme(theme: Int) {
    val Purple80 = Color(0xFFD0BCFF)
    val PurpleGrey80 = Color(0xFFCCC2DC)
    val Pink80 = Color(0xFFEFB8C8)

    val Purple40 = Color(0xFF6650a4)
    val PurpleGrey40 = Color(0xFF625b71)
    val Pink40 = Color(0xFF7D5260)
    val DarkPurple = Color(0xFF2A2A3F)

    val Add = when(theme) {
        0-> Color(0xFF2A2A3F)
        1-> Color(0xFF383838)
        2-> Color(0xFFFFFFFF)
        3-> Color(0xFF000000)
        else -> Color(0xFF2A2A3F)
    }
    val TextColorWhite = when (theme) {
        0-> Color(0xFF000000)
        1-> Color(0xFF333333)
        2-> Color(0xFFFFFFFF)
        3-> Color(0xFF222222)
        else -> Color(0xFF2A2A3F)
    }

    val EmotionRow = when (theme) {
        0-> Color(0xFF898A8D)
        1-> Color(0xFF9F9891)
        2-> Color(0xFF3D8DD7)
        3-> Color(0xFF628469)
        else -> Color(0xFF2A2A3F)
    }

    val TextColorBlack = when (theme) {
        0-> Color(0xFF2A2A3F)
        1-> Color(0xFF383838)
        2-> Color(0xFF494949)
        3-> Color(0xFF222222)
        else -> Color(0xFF2A2A3F)
    }

    val NavigationBar = when(theme) {
        0-> Color(0xFF373660)
        1-> Color(0xFF383838)
        2-> Color(0xFF494949)
        3-> Color(0xFF242424)
        else -> Color(0xFF373660)
    }
    val Background = when(theme) {
        0 -> Color(0xFFFFFFFF)
        1 -> Color(0xFFFFFFFF)
        2 -> Color(0xFF383838)
        3 -> Color(0xFFFEF1E0)
        else -> Color(0xFFFFFFFF)
    }
    val BasicBox = when(theme) {
        0-> Color(0xFFFFC967)
        1-> Color(0xFF9F9891)
        2-> Color(0xFF316DA5)
        3-> Color(0xFFBB6A4A)
        else -> Color(0xFFFFC967)
    }
    val BorderColor = when(theme) {
        0-> Color(0xFFFCD58F)
        1-> Color(0xFF9F9891)
        2-> Color(0xFF316DA5)
        3-> Color(0xFFBB6A4A)
        else -> Color(0xFFFCD58F)
    }
    val Container = when(theme) {
        0 -> Color(0xFFA54D46)
        1 -> Color(0xFF974440)
        2 -> Color(0xFF316DA5)
        3 -> Color(0xFFBB6A4A)
        else -> Color(0xFFA54D46)
    }


}