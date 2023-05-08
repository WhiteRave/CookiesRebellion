package com.example.myapplication2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.datathnig.ItemOffsetDecoration
import com.example.myapplication2.zadachi.*
import java.util.concurrent.TimeUnit

class dom : Fragment() {

    private val ELEMENTS_KEY = "elements"
    private val LAST_GENERATED_KEY = "lastGenerated"

    private lateinit var randomElementTextView: TextView
    private lateinit var elements: List<String>
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:TodayTasksAdapter
    private var taskList = mutableListOf<Taskotem>()
    private lateinit var taskRepository1: TaskRepository1


    override fun onAttach(context: Context) {
        super.onAttach(context)
        taskRepository1 = TaskRepository1(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskList = taskRepository1.loadTasks().toMutableList()
        adapter = TodayTasksAdapter(taskList)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dom, container, false)
    }

    private fun is24HoursPassed(lastGenerated: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastGenerated
        return elapsedTime >= TimeUnit.MINUTES.toMillis(1)
    }


    private fun saveGenerated(randomElement: String) {
        sharedPreferences.edit()
            .putString(ELEMENTS_KEY, randomElement)
            .putLong(LAST_GENERATED_KEY, System.currentTimeMillis())
            .apply()
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        randomElementTextView = view.findViewById(R.id.randomElementTextView)
        sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        elements = requireContext().resources.openRawResource(R.raw.elements).bufferedReader().readLines()

        val lastGenerated = sharedPreferences.getLong(LAST_GENERATED_KEY, 0L)
        if (is24HoursPassed(lastGenerated)) {
            val randomIndex = (0 until elements.size).random()
            val randomElement = elements[randomIndex]
            randomElementTextView.text = randomElement
            saveGenerated(randomElement)
        } else {
            val lastGeneratedElement = sharedPreferences.getString(ELEMENTS_KEY, "")
            randomElementTextView.text = lastGeneratedElement
        }


        val viewModel = ViewModelProvider(this).get(com.example.myapplication2.zadachi.ViewModel::class.java)
        recyclerView = view.findViewById(R.id.recycle)
        recyclerView.addItemDecoration(ItemOffsetDecoration(16))
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        val swipeToDeleteCallback = SwipeToDeleteCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val newTaskList = tasks.filterNot { task -> taskList.any { it.name == task } }
                .map { newTask -> Taskotem(newTask) }
            taskList.addAll(newTaskList)
            adapter.notifyItemRangeInserted(taskList.size - newTaskList.size, newTaskList.size)
        }



    }


    override fun onDestroyView() {
        super.onDestroyView()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.todoListRecyclerView1)
        recyclerView?.adapter = null
        taskRepository1.saveTasks(taskList)
        adapter.notifyDataSetChanged()


    }

}
