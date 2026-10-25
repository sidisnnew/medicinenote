package com.example.kotlin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var medicine : TextView
    var interval : TextView

    init {
        medicine = itemView.findViewById(R.id.medicine)
        interval = itemView.findViewById(R.id.interval)
    }
}