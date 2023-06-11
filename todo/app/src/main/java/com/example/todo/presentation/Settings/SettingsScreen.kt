package com.example.todo.presentation.Settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.R
import com.example.todo.presentation.ui.theme.ColorTheme

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val state = settingsViewModel.stateSettings.collectAsState().value
    val colorTheme = ColorTheme(state.theme)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorTheme.Background),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
          modifier = Modifier
              .padding(20.dp)
              .fillMaxWidth(),
            text = "Темы",
            textAlign = TextAlign.Center,
            fontSize = 21.sp,
            color = colorTheme.TextColorWhite
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ImageTheme(
                    idImage = if (state.theme == 0) R.drawable.theme0on else R.drawable.theme0off,
                    modifier = Modifier.clickable {
                        settingsViewModel.changeTheme(0)
                    }
                )
                ImageTheme(idImage = if (state.theme == 2) R.drawable.theme2on else R.drawable.theme2off,
                    modifier = Modifier.clickable {
                        settingsViewModel.changeTheme(2)
                    })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ImageTheme(idImage = if (state.theme == 1) R.drawable.theme1on else R.drawable.theme1off,
                    modifier = Modifier.clickable {
                        settingsViewModel.changeTheme(1)
                    })
                ImageTheme(idImage = if (state.theme == 3) R.drawable.theme3on else R.drawable.theme3off,
                    modifier = Modifier.clickable {
                        settingsViewModel.changeTheme(3)
                    })
            }

        }

        
    }


}

@Composable
fun ImageTheme(idImage: Int, modifier: Modifier) {
    Image(
        modifier = modifier.width(114.dp).height(28.dp),
        painter = painterResource(id = idImage), contentDescription = ""
    )
}
