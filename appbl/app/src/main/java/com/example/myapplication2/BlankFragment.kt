package com.example.myapplication2


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication2.datathnig.SharedViewModel
import com.example.myapplication2.datathnig.TaskItem


class BlankFragment : Fragment() {
    private lateinit var btno: AppCompatImageButton
    private lateinit var Text: TextView
    companion object {
        fun newInstance(item: String) = BlankFragment().apply {
            arguments = Bundle().apply {
                putString("item", item)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_blank, container, false)

        btno = view.findViewById(R.id.imageButton)
        Text = view.findViewById(R.id.tekstik)

        btno.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_BlankFragment_to_cheklist)
        }

        val sharedViewModel = activity?.run {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        }

        val taskText = sharedViewModel?.tasks?.value?.joinToString(", ")
        Text.text = taskText


        return view
    }

}


