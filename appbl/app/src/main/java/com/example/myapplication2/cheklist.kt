package com.example.myapplication2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.datathnig.*
import kotlinx.coroutines.launch


class cheklist : Fragment() {
    private lateinit var btn2: MaterialButton
    private var taskList = mutableListOf<TaskItem>()
    lateinit var adapter: TaskAdapter
    private lateinit var taskRepository: TaskRepository
    private lateinit var searchLayout: LinearLayout
    private lateinit var searchButton: ImageButton
    private lateinit var searchCancelButton: ImageButton
    private lateinit var searchEditText: EditText
    private var isDataLoaded = false









    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskRepository = context?.let { TaskRepository(it) }!!
        taskList = taskRepository.loadTasks().toMutableList()
        adapter = TaskAdapter(taskList)
        Log.d("Fragment", "onCreate called")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("taskList", ArrayList(taskList))
        Log.d("Fragment", "onSaveInstanceState called")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val savedTaskList = savedInstanceState.getParcelableArrayList<TaskItem>("taskList")
            taskList.clear()
            savedTaskList?.let { taskList.addAll(it) }
            adapter.notifyDataSetChanged()
            Log.d("Fragment", "onViewStateRestored called")
        }
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


        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val recyclerView = view.findViewById<RecyclerView>(R.id.todoListRecyclerView)
        recyclerView.addItemDecoration(ItemOffsetDecoration(16))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        sharedViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val newTaskList = tasks.filterNot { task -> taskList.any { it.name == task } }
                .map { newTask -> TaskItem(newTask) }
            taskList.addAll(newTaskList)
            adapter.notifyItemRangeInserted(taskList.size - newTaskList.size, newTaskList.size)
        }







        




    }


    override fun onDestroyView() {
        super.onDestroyView()

        // Сохраняем задачи в репозиторий
        lifecycleScope.launch {
            taskRepository.saveTasks(taskList)
        }

        // Очищаем адаптер и удаляем ссылку на RecyclerView
        val recyclerView = view?.findViewById<RecyclerView>(R.id.todoListRecyclerView)
        recyclerView?.adapter = null
        taskList.clear()

        Log.d("Fragment", "onDestroyView called")
    }









}




