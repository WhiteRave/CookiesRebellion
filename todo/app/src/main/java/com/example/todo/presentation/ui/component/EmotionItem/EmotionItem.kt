package com.example.todo.presentation.ui.component.EmotionItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.data.local.model.Day
import com.example.todo.presentation.Settings.SettingsViewModel
import com.example.todo.presentation.screens.Emotion.EmotionViewModel
import com.example.todo.presentation.ui.theme.ColorTheme

@Composable
fun EmotionItem(item: Day, viewModel: EmotionViewModel, settingsViewModel: SettingsViewModel) {
    val state = settingsViewModel.stateSettings.collectAsState()
    val colorTheme = ColorTheme(state.value.theme)
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(colorTheme.EmotionRow),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        ImageEmotion(img = R.drawable.angry, item, Modifier.weight(1f), viewModel)
        ImageEmotion(img = R.drawable.pain, item, Modifier.weight(1f), viewModel)
        ImageEmotion(img = R.drawable.norm, item, Modifier.weight(1f), viewModel)
        ImageEmotion(img = R.drawable.happy, item, Modifier.weight(1f), viewModel)
        ImageEmotion(img = R.drawable.veryhappy, item, Modifier.weight(1f), viewModel)
        Text(
            modifier = Modifier
                .padding(3.dp)
                .weight(1f),
            text = item.date,
            color = colorTheme.TextColorWhite,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun ImageEmotion(img: Int, item: Day, modifier: Modifier, viewModel: EmotionViewModel) {
    val map = mapOf(
        R.drawable.angry to "angry",
        R.drawable.pain to "pain",
        R.drawable.norm to "norm",
        R.drawable.happy to "happy",
        R.drawable.veryhappy to "veryhappy"
    )
    Image(
        modifier = modifier
            .size(45.dp)
            .padding(vertical = 5.dp)
            .alpha(if(map[img]==item.emotion) 1f else 0.3f)
            .clickable {
                       viewModel.changeEmotion(emotion = map[img].toString(), date = item.date)
            },
        painter = painterResource(id = img),
        contentDescription = ""
    )
}