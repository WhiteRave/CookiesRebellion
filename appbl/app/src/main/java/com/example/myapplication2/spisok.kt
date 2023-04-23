@file:Suppress("UNREACHABLE_CODE")

package com.example.myapplication2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
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
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var btnNav: MaterialButton
    private lateinit var btnNav3: MaterialButton
    private lateinit var btnNav4: MaterialButton
    private lateinit var searchLayout: LinearLayout
    private lateinit var searchButton: ImageButton
    private lateinit var searchCancelButton: ImageButton
    private lateinit var searchEditText: EditText


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
        val view: View = inflater.inflate(R.layout.fragment_spisok, container, false)


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






        btnNav =view.findViewById(R.id.button)
        btnNav.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_fullscreen_to_cheklist2)

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
