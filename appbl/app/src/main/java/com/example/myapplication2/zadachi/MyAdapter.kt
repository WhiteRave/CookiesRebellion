package com.example.myapplication2.zadachi

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R
import java.text.SimpleDateFormat
import java.util.*


open class MyAdapter(
    open var taskList: MutableList<Taskotem>, var onItemClickListener: OnItemClickListener? = null, var onItemClick: ((Taskotem) -> Unit)? = null
) : RecyclerView.Adapter<MyAdapter.TaskotemViewHolder>() {






    @SuppressLint("ClickableViewAccessibility")
    inner class TaskotemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView: TextView = itemView.findViewById(R.id.namee)
        val taskNameTextView1: TextView = itemView.findViewById(R.id.dueTime)
        val taskImage: ImageButton = itemView.findViewById(R.id.img)
        var taskItem: Taskotem? = null


        // тут кароче текст нажатие на него понятно и тд
        fun bind(taskotem: Taskotem) {
            this.taskItem = taskotem
            taskNameTextView.text = taskotem.name
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            taskNameTextView1.text = taskotem.dueDate?.let { dateFormat.format(it) }


            taskNameTextView.setOnClickListener {
                taskotem?.let { it1 -> onItemClick?.invoke(it1) }
            }
            taskImage.setOnClickListener {
                showPopup(taskItem!!)
            }
            taskNameTextView1.setOnClickListener {
            }

        }




        private fun showPopup(taskotem: Taskotem) {
            val popup = PopupMenu(itemView.context, taskImage)
            popup.inflate(R.menu.menu_item1)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.data -> {
                        val calendar = Calendar.getInstance()
                        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                            // Здесь устанавливаем выбранную дату в объект календаря
                            calendar.set(Calendar.YEAR, year)
                            calendar.set(Calendar.MONTH, monthOfYear)
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                            // Здесь форматируем дату в нужном формате и устанавливаем ее в TextView
                            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                            val dueTimeTextView = itemView.findViewById<TextView>(R.id.dueTime)
                            dueTimeTextView.text = dateFormat.format(calendar.time)

                            val newDate = calendar.time
                            taskotem.dueDate = newDate
                            notifyDataSetChanged()
                        }

                        // Здесь открываем диалог календаря
                        DatePickerDialog(itemView.context, dateSetListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show()

                        true
                    }
                    R.id.time -> {
                        true
                    }
                    R.id.important -> {
                        taskotem.apply {
                            if (isImportant) {
                                uncheckedIcon = R.drawable.baseline_star_border_24
                                checkedIcon = R.drawable.baseline_star_24
                            } else {
                                uncheckedIcon = R.drawable.baseline_radio_button_unchecked_24
                                checkedIcon = R.drawable.baseline_check_circle_outline_24
                            }
                            isImportant = !isImportant
                        }
                        notifyDataSetChanged()
                        true
                    }


                    else -> false
                }
            }
            popup.show()
        }













    }
    fun removeItem(position: Int) {
        taskList.removeAt(position)
    }
    fun getItem(position: Int): String {
        return taskList[position].toString()
    }
    fun moveItem(from: Int, to: Int) {
        if (from < to) {
            for (i in from until to) {
                Collections.swap(taskList, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(taskList, i, i - 1)
            }
        }
        notifyItemMoved(from, to)
    }








    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskotemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item1, parent, false)
        return TaskotemViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: TaskotemViewHolder, position: Int) {

        val task = taskList.get(position)
        holder.bind(task)
        holder.itemView.findViewById<ImageButton>(R.id.completeButton).setOnClickListener {
            task.isCompleted = !task.isCompleted
            updateTaskCompletionState(holder.itemView, task)
        }



        //диалог ебана рот
        updateTaskCompletionState(holder.itemView, task)
        holder.itemView.findViewById<TextView>(R.id.namee).setOnClickListener {
            val context = holder.itemView.context
            val editText = EditText(context)
            editText.setText(task.name) // Ебашим текущее имя задачи в текстовое поле
            editText.setSelection(editText.text.length) // Хуярим курсор в конец текста

            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Переименовать")
            alertDialog.setView(editText)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton("ОК") { dialog, which ->
                val newName = editText.text.toString()
                task.name = newName
                notifyDataSetChanged()

                // скрыть клаву
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
            }
            alertDialog.setNegativeButton("Отмена") { dialog, _ ->
                // то же самое епта
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
                dialog.dismiss()
            }

            val dialog = alertDialog.create()
            //здесь кароч херня делется чтоб нельзя было ввести чето когда там нихуя не изменилось + цвет меняет
            dialog.setOnShowListener {
                val button1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                val textColor = ContextCompat.getColor(context, android.R.color.holo_purple)
                button1.setTextColor(textColor)

                val button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                button.isEnabled = false
                button.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))

                editText.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        button.isEnabled = !s.isNullOrEmpty()
                        if (button.isEnabled) {
                            button.setTextColor(textColor)
                        } else {
                            button.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                        }
                    }
                })
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                editText.postDelayed({
                    editText.requestFocus()
                    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
                }, 100)
            }
            dialog.show()
        }
    }



    //ну тут апдейтим нажатием понятно кароч
    private fun updateTaskCompletionState(view: View, task: Taskotem) {
        val completeButton = view.findViewById<ImageButton>(R.id.completeButton)
        val nameTextView = view.findViewById<TextView>(R.id.namee)

        if (task.isCompleted) {
            task.isCheckedIcon = (task.checkedIcon != R.drawable.baseline_check_circle_outline_24) // проверяем, установлен ли свойство checkedIcon
            completeButton.setImageResource(task.checkedIcon)
            nameTextView.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(ContextCompat.getColor(context, androidx.appcompat.R.color.abc_hint_foreground_material_dark))
            }
        } else {
            task.isCheckedIcon = false // сбрасываем значение isCheckedIcon
            completeButton.setImageResource(task.uncheckedIcon)
            nameTextView.apply {
                paintFlags = 0
                val typedArray = context.obtainStyledAttributes(intArrayOf(android.R.attr.textColorPrimaryNoDisable))
                val color = typedArray.getColor(0, Color.BLACK)
                typedArray.recycle()
                setTextColor(color)
            }
        }
    }



    override fun getItemCount(): Int {
        return taskList.size
    }








}

