package com.example.myapplication2.zadachi

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import java.text.SimpleDateFormat
import java.util.*

class TodayTasksAdapter(override var taskList: MutableList<Taskotem>) : MyAdapter(taskList.toMutableList()) {

    override fun getItemCount(): Int {
        val currentDate = Calendar.getInstance().time
        val todayTasks = taskList.filter { task ->
            val taskDate = task.dueDate
            taskDate?.year == currentDate.year && taskDate.month == currentDate.month && taskDate.day == currentDate.day && task.isCompleted == false
        }
        return todayTasks.size
    }

    override fun onBindViewHolder(holder: TaskotemViewHolder, position: Int) {
        val currentDate = Calendar.getInstance().time
        val todayTasks = taskList.filter { task ->
            val taskDate = task.dueDate
            taskDate?.year == currentDate.year && taskDate.month == currentDate.month && taskDate.day == currentDate.day && task.isCompleted == false
        }
        holder.taskNameTextView.text = todayTasks[position].name
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        holder.taskNameTextView1.text = dateFormat.format(todayTasks[position].dueDate)
    }
}





