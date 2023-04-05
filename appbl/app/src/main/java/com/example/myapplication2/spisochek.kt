package com.example.myapplication2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

@Suppress("DEPRECATION")
class spisochek : Fragment() {
    private lateinit var btno: AppCompatImageButton
    private lateinit var Text: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        dialog()
        val view: View = inflater.inflate(R.layout.fragment_spisochek, container, false)


        btno =view.findViewById(R.id.imageButton)
        btno.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_spisochek_to_cheklist)

        }


        Text = view.findViewById(R.id.tekstik)

        return view;
    }
    private fun dialog(){


        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.activity_main2, null)
        val ed = dialogLayout.findViewById<EditText>(R.id.userNo)



        builder.setView(ed)
        builder.setTitle("Создание списка")
        builder.setPositiveButton("Создать"){dialog, i ->
            Text.text = ed.text.toString()
        }
        builder.setNegativeButton("Отмена") { dialog, i ->
            findNavController().navigateUp()}



        val dialog = builder.create()
        dialog.setOnShowListener {
            val button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            button.isEnabled = false
            ed.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    button.isEnabled = !s.isNullOrEmpty()
                }
            })
        }
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
    }
}