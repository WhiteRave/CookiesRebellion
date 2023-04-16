@file:Suppress("UNREACHABLE_CODE")

package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class spisok : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var btnNav: MaterialButton
    private lateinit var btnNav2: MaterialButton
    private lateinit var btnNav3: MaterialButton
    private lateinit var btnNav4: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_spisok, container, false)

        btnNav =view.findViewById(R.id.button)
        btnNav.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_fullscreen_to_cheklist2)

        }
        btnNav2 =view.findViewById(R.id.button2)
        btnNav2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fullscreen_to_idk)
        }
        btnNav3 =view.findViewById(R.id.button3)
        btnNav3.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fullscreen_to_idk2)
        }
        btnNav4 =view.findViewById(R.id.button4)
        btnNav4.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_fullscreen_to_importnant)
        }
        return view;
        
    }


}
