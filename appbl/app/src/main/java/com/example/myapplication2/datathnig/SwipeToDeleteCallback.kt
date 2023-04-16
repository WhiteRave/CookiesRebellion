package com.example.myapplication2.datathnig

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(private val adapter: TaskAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // Мы не реализуем перетаскивание, поэтому возвращаем false
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Получаем позицию удаляемого элемента
        val position = viewHolder.adapterPosition
        // Удаляем элемент из списка
        adapter.deleteTask(position)
    }
}