package com.example.myapplication2

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myapplication2.datathnig.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class cheklist : Fragment() {
    private lateinit var btn2: MaterialButton
    private var taskList = mutableListOf<TaskItem>()
    lateinit var adapter: TaskAdapter
    private lateinit var taskRepository: TaskRepository
    private var isTaskListInitialized = false
    private lateinit var searchLayout: LinearLayout
    private lateinit var searchButton: ImageButton
    private var taskSet = mutableSetOf<TaskItem>()
    private lateinit var searchCancelButton: ImageButton
    private lateinit var searchEditText: EditText



    override fun onAttach(context: Context) {
        super.onAttach(context)
        taskRepository = TaskRepository(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskList = taskRepository.loadTasks().toMutableList()
        adapter = TaskAdapter(taskList)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_cheklist, container, false)

        btn2 =view.findViewById(R.id.button)
        btn2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_cheklist_to_spisochek)
        }

        taskRepository.saveTasks(taskList)
        adapter.notifyDataSetChanged()

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchLayout = view.findViewById(R.id.searchLayout)
        searchButton = view.findViewById(R.id.searchButton)
        searchCancelButton = view.findViewById(R.id.searchCancelButton)
        searchEditText = view.findViewById(R.id.searchEditText)

        searchButton.setOnClickListener {
            searchLayout.visibility = View.VISIBLE
            searchButton.visibility = View.GONE
            searchEditText.requestFocus()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
        }

        searchCancelButton.setOnClickListener {
            searchLayout.visibility = View.GONE
            searchButton.visibility = View.VISIBLE
            searchEditText.clearFocus()
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }


        val recyclerView = view.findViewById<RecyclerView>(R.id.todoListRecyclerView)
        recyclerView.addItemDecoration(ItemOffsetDecoration(16))
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter





        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)





        sharedViewModel.inputText.observe(viewLifecycleOwner) { inputText ->
            inputText?.let {
                if (it.isNotBlank() && !sharedViewModel.tasks.value.orEmpty().contains(it)) {
                    sharedViewModel.addTask(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }


        sharedViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            tasks.forEach { task ->
                val taskItem = TaskItem(task)
                if (!taskList.contains(taskItem)) {
                    taskList.add(taskItem)
                    adapter.notifyDataSetChanged()
                }
            }
        }





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
        val recyclerView = view?.findViewById<RecyclerView>(R.id.todoListRecyclerView)
        recyclerView?.adapter = null
        taskRepository.saveTasks(taskList)
        adapter.notifyDataSetChanged()


    }

}




