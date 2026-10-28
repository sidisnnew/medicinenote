package com.example.kotlin

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var medicine : TextView
    var interval : TextView
    var date : TextView
    val taken : Button

    init {
        medicine = itemView.findViewById(R.id.medicine)
        interval = itemView.findViewById(R.id.interval)
        date = itemView.findViewById(R.id.time)
        taken = itemView.findViewById<AppCompatButton>(R.id.taken)
    }
}