package com.example.myapplication2

import android.app.AlertDialog
import android.content.Intent
import android.location.GnssAntennaInfo.Listener
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.navigation.Navigation
import com.example.myapplication2.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class cheklist : Fragment() {
    private lateinit var btn1: AppCompatImageButton
    private lateinit var btn2: MaterialButton

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

}