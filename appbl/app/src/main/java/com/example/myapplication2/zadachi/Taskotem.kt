package com.example.myapplication2.zadachi
import android.content.Context
import androidx.core.content.ContextCompat
import com.example.myapplication2.R
import java.time.LocalDate
import java.util.*

class Taskotem(
    var name: String,
    var isCompleted: Boolean = false,
    var isImportant: Boolean = true,
    var isCheckedIcon: Boolean = false,
    var id: UUID = UUID.randomUUID(),
    var dueDate: Date? = null,
    var uncheckedIcon: Int = R.drawable.baseline_radio_button_unchecked_24,
    var checkedIcon: Int = R.drawable.baseline_check_circle_outline_24
)