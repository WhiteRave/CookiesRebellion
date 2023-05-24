package com.example.myapplication2


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.datathnig.ItemOffsetDecoration
import com.example.myapplication2.datathnig.TaskItem
import com.example.myapplication2.datathnig.TaskRepository
import com.example.myapplication2.zadachi.MyAdapter
import com.example.myapplication2.zadachi.SwipeToDeleteCallback
import com.example.myapplication2.zadachi.TaskRepository1
import com.example.myapplication2.zadachi.Taskotem
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.time.LocalDate
import java.util.*


class BlankFragment : Fragment() {
    private lateinit var btno: AppCompatImageButton
    private lateinit var btna: ExtendedFloatingActionButton
    private lateinit var Text: TextView
    private lateinit var inputText: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskRepository1: TaskRepository1
    private lateinit var adapter: MyAdapter
    private var taskList = mutableListOf<Taskotem>()
    private var selectedDate: LocalDate? = null




    override fun onAttach(context: Context) {
        super.onAttach(context)
        taskRepository1 = TaskRepository1(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inputText = arguments?.getString("inputText", "") ?: ""
        taskList = taskRepository1.loadTasks().toMutableList()
        adapter = MyAdapter(taskList)
        Log.d("Fragment", "onCreate called")

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_blank, container, false)

        btno = view.findViewById(R.id.imageButton)
        btna = view.findViewById(R.id.newTaskButton)
        Text = view.findViewById(R.id.tekstik)
        Text.text = inputText

        btno.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_BlankFragment_to_cheklist)
        }

        val text = arguments?.getString("text")
        if (text != null) {
            Text.text = text
        }
        btna.setOnClickListener {
            dialog()
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(com.example.myapplication2.zadachi.ViewModel::class.java)
        recyclerView = view.findViewById(R.id.todoListRecyclerView1)
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

    private fun dialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.activity_main3, null)

        val editText = dialogLayout.findViewById<EditText>(R.id.userNo1)
        editText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)


        builder.setTitle("Добавление задачи")
        builder.setView(dialogLayout)


        builder.setPositiveButton("Добавить") { dialog, i ->
            val viewModel = ViewModelProvider(this).get(com.example.myapplication2.zadachi.ViewModel::class.java)

            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
            val taskText = editText.text.toString()
            viewModel.addTask(taskText)






            dialog.dismiss()
        }

        builder.setNegativeButton("Отмена") { dialog, i ->
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
            dialog.cancel()
        }


        val dialog = builder.create()
        dialog.setOnShowListener {
            val button1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            val textColor = ContextCompat.getColor(requireContext(), android.R.color.holo_purple)
            button1.setTextColor(textColor)
            val button2 = dialog.getButton(DialogInterface.BUTTON_NEUTRAL)
            val textColor2 = ContextCompat.getColor(requireContext(), android.R.color.holo_purple)
            button2.setTextColor(textColor2)

            val button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            button.isEnabled = false
            button.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.darker_gray
                )
            )

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    button.isEnabled = !s.isNullOrEmpty()
                    if (button.isEnabled) {
                        button.setTextColor(textColor)
                    } else {
                        button.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                android.R.color.darker_gray
                            )
                        )
                    }
                }
            })
        }




        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)




        dialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                true
            } else {
                false
            }
        }
    }







    override fun onDestroyView() {
        super.onDestroyView()
        val recyclerView = view?.findViewById<RecyclerView>(R.id.todoListRecyclerView1)
        recyclerView?.adapter = null
        taskRepository1.saveTasks(taskList)
        adapter.notifyDataSetChanged()
        Log.d("Fragment", "onDestroy called")


    }

}

