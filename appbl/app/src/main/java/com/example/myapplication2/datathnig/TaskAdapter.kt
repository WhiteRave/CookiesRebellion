package com.example.myapplication2.datathnig

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.R

class TaskAdapter(
    var taskList: MutableList<TaskItem>?,
    var onItemClick: ((TaskItem) -> Unit)? = null,
    var onItemSelectedListener: OnItemSelectedListener? = null
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    interface OnItemSelectedListener {
        fun onItemSelected(taskItem: TaskItem)
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskNameTextView: TextView = view.findViewById(R.id.mTitle)
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
                        // Действие при выборе пункта меню "Rename"
                        true
                    }
                    R.id.delete -> {
                        taskList?.remove(taskItem)
                        notifyDataSetChanged()
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


    fun updateTaskText(position: Int, newTaskText: String) {
        taskList?.get(position)?.name = newTaskText
        notifyItemChanged(position)
    }
}




