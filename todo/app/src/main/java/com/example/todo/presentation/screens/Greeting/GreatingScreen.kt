package com.example.todo.presentation.screens.Greeting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.todo.R
import com.example.todo.presentation.navigation.NavigationViewModel
import com.example.todo.presentation.navigation.Screens
import com.example.todo.presentation.ui.component.GreetingRow
import com.example.todo.presentation.ui.theme.ColorTheme
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingScreen(navController: NavController, viewModel: NavigationViewModel) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(40.dp)

    ) {
        Text(
            text = "Добро пожаловать в \nLeaves",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 80.dp, start = 15.dp, end = 15.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(10.dp))

        GreetingRow(
            title = "Создавайте списки \n" +
                    "быстро и легко", image = R.drawable.pencil
        )

        GreetingRow(title = "Напоминания о задачах", image = R.drawable.watch)

        GreetingRow(title = "Пользовательские темы", image = R.drawable.phone)

        GreetingRow(title = "Мотивация на каждый день", image = R.drawable.medal)

        Button(
            onClick = {
                navController.navigate(Screens.Main.rout) {
                    popUpTo(0)
                }
                viewModel.isReady = true
            },
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorTheme(0).DarkPurple
            )

        ) {
            Text(
                text = "ПРОДОЛЖИТЬ",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
            )
            Icon(
                imageVector = Icons.Default.ArrowRight,
                tint = Color.White,
                contentDescription = "стрелка"
            )
        }
    }
}