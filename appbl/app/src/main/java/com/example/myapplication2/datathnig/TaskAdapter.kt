package com.example.myapplication2.datathnig

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import java.util.*

class TaskAdapter(
    var taskList: MutableList<TaskItem>?,
    var onItemClick: ((TaskItem) -> Unit)? = null,
    var onItemSelectedListener: OnItemSelectedListener? = null,
    
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(taskItem: TaskItem)
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskNameTextView: TextView = view.findViewById(R.id.mTitle)
        private val taskId = UUID.randomUUID().toString()
        private val secondRecyclerView = itemView.findViewById<RecyclerView>(R.id.todoListRecyclerView1)
        private val taskImageBtn: ImageButton = view.findViewById(R.id.img)
        var taskItem: TaskItem? = null

        init {
            itemView.setOnClickListener {
                taskItem?.let { it1 -> onItemClick?.invoke(it1) }
                taskItem?.let { it1 -> onItemSelectedListener?.onItemSelected(it1) }
            }
            taskImageBtn.setOnClickListener {
                showPopup(taskItem!!)
            }
        }

        fun bind(taskItem: TaskItem) {
            this.taskItem = taskItem
            taskNameTextView.text = taskItem.name
        }
        private fun showPopup(taskItem: TaskItem) {
            val popup = PopupMenu(itemView.context, taskImageBtn)
            popup.inflate(R.menu.menu_item)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.rename -> {
                        val builder = AlertDialog.Builder(itemView.context)
                        builder.setTitle("Переименовать список")

                        val input = EditText(itemView.context)
                        input.setText(taskItem.name)
                        builder.setView(input)

                        builder.setPositiveButton("OK") { _, _ ->
                            val updatedName = input.text.toString()
                            taskItem.name = updatedName
                            notifyDataSetChanged()
                            // Скрыть клавиатуру
                            val inputMethodManager = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(input.windowToken, 0)
                        }

                        builder.setNegativeButton("Отмена") { dialog, _ ->
                            // Скрыть клавиатуру
                            val inputMethodManager = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(input.applicationWindowToken, 0)
                            dialog.dismiss()
                        }
                        val dialog = builder.create()
                        dialog.setOnShowListener {
                            val button1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                            val textColor = ContextCompat.getColor(itemView.context, android.R.color.holo_purple)
                            button1.setTextColor(textColor)

                            val button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                            button.isEnabled = false
                            button.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.darker_gray))

                            input.addTextChangedListener(object : TextWatcher {
                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                                override fun afterTextChanged(s: Editable?) {
                                    button.isEnabled = !s.isNullOrEmpty()
                                    if (button.isEnabled) {
                                        button.setTextColor(textColor)
                                    } else {
                                        button.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.darker_gray))
                                    }
                                }
                            })
                        }


                        dialog.setCanceledOnTouchOutside(false)

                        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                        dialog.show()
                        // Отобразить клавиатуру
                        input.requestFocus()
                        val inputMethodManager = itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

                        true
                    }
                    R.id.delete -> {
                        taskList?.remove(taskItem)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, taskList!!.size)
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList?.get(position)
        if (currentTask != null) {
            holder.bind(currentTask)
            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("text", currentTask.name)
                val navController = Navigation.findNavController(holder.itemView)
                navController.navigate(R.id.action_cheklist_to_BlankFragment, bundle)
            }

        } else {
            holder.taskNameTextView.text = ""
        }
    }

    override fun getItemCount(): Int {
        return taskList?.size ?: 0
    }


}




