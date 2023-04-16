package com.example.myapplication2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.datathnig.*


class cheklist : Fragment() {
    private lateinit var btn1: AppCompatImageButton
    private lateinit var btn2: MaterialButton
    private lateinit var taskList: MutableList<TaskItem>
    lateinit var adapter: TaskAdapter
    private var isTaskListInitialized = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_cheklist, container, false)


        btn1 =view.findViewById(R.id.imageButton)
        btn1.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_cheklist_to_fullscreen)
        }
        btn2 =view.findViewById(R.id.button)
        btn2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_cheklist_to_spisochek)

        }

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view.findViewById<RecyclerView>(R.id.todoListRecyclerView)
        recyclerView.addItemDecoration(ItemOffsetDecoration(16))
        recyclerView.layoutManager = LinearLayoutManager(activity)
        taskList = mutableListOf()
        adapter = TaskAdapter(taskList)
        recyclerView.adapter = adapter


        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)


        sharedViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            if (!isTaskListInitialized) {
                taskList.clear()
                isTaskListInitialized = false
            }

            taskList.addAll(tasks.map { TaskItem(it) })
            adapter.notifyDataSetChanged()
        }



        sharedViewModel.inputText.observe(viewLifecycleOwner) { inputText ->
            inputText?.let {
                if (it.isNotBlank() && !sharedViewModel.tasks.value.orEmpty().contains(it)) {
                    sharedViewModel.addTask(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        adapter.onItemClick = { taskItem: TaskItem ->
            val position = taskList.indexOf(taskItem)
            val bundle = Bundle()
            bundle.putString("taskText", taskItem.name)

            recyclerView.layoutManager?.findViewByPosition(position)?.let { itemView ->

                val btna = itemView.findViewById<CardView>(R.id.card)
                btna?.setOnClickListener{
                    sharedViewModel?.updateTasks(taskItem.name)
                    Navigation.findNavController(view).navigate(R.id.action_cheklist_to_BlankFragment)
                }

                Navigation.findNavController(view).navigate(R.id.action_cheklist_to_BlankFragment)
            }
        }








    }
    override fun onDestroyView() {
        super.onDestroyView()
        taskList.clear()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.todoListRecyclerView)
        recyclerView?.adapter = null
    }

}




