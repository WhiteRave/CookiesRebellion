package com.example.myapplication2

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myapplication2.datathnig.SharedViewModel
import com.example.myapplication2.datathnig.TaskAdapter
import com.example.myapplication2.datathnig.TaskItem
import com.example.myapplication2.datathnig.TaskRepository
import com.google.android.material.navigation.NavigationView
import kotlin.random.Random


@Suppress("DEPRECATION")
class spisochek : Fragment() {
    private lateinit var btno: AppCompatImageButton
    private lateinit var Text: TextView
    private var dialog: AlertDialog? = null
    private lateinit var mContext: Context
    lateinit var adapter: TaskAdapter
    private var taskList = mutableListOf<TaskItem>()
    private lateinit var taskRepository: TaskRepository


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = requireContext()
        taskRepository = TaskRepository(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskList = taskRepository.loadTasks().toMutableList()
        adapter = TaskAdapter(taskList,)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog()
        val view: View = inflater.inflate(R.layout.fragment_spisochek, container, false)
        Text = view.findViewById(R.id.tekstik)
        btno = view.findViewById(R.id.imageButton)
        btno.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_spisochek_to_cheklist)

        }
        return view
    }

    private fun dialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.activity_main2, null)
        val ed = dialogLayout.findViewById<EditText>(R.id.userNo)
        ed.requestFocus()
        builder.setView(ed)
        builder.setTitle("Создание списка")

        builder.setPositiveButton("Создать") { dialog, i ->
            Text.text = ed.text.toString()

            val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
            val inputText = ed.text.toString()
            val bundle = Bundle()
            bundle.putString("inputText", inputText)
            sharedViewModel.addTask(inputText)










            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(ed.windowToken, 0)
            dialog.dismiss()
            view?.let { Navigation.findNavController(it).navigate(R.id.action_spisochek_to_BlankFragment, bundle) }
        }



        builder.setNegativeButton("Отмена") { dialog, i ->
            findNavController().navigateUp()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(ed.windowToken, 0)
            dialog.dismiss()
        }

        val dialog = builder.create()
        this.dialog = dialog
        dialog.setOnShowListener {
            val button1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            val textColor = ContextCompat.getColor(requireContext(), android.R.color.holo_purple)
            button1.setTextColor(textColor)

            val button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            button.isEnabled = false
            button.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))

            ed.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    button.isEnabled = !s.isNullOrEmpty()
                    if (button.isEnabled) {
                        button.setTextColor(textColor)
                    } else {
                        button.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
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

}
