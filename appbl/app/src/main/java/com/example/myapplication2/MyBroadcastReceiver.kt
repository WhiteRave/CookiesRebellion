package com.example.myapplication2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.TextView

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val elements = context.resources.openRawResource(R.raw.elements).bufferedReader().readLines()
        val randomIndex = (0 until elements.size).random()
        val randomElement = elements[randomIndex]
        val randomElementTextView = (context as MainActivity).findViewById<TextView>(R.id.randomElementTextView)
        randomElementTextView.text = randomElement
    }
}
