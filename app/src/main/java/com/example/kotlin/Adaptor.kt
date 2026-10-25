package com.example.kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adaptor(var context: Context, var mData: Array<String>, var iData: Array<Int>) : RecyclerView.Adapter<ViewHolder>(){

    private var listener: OnRecyclerViewClickListener? = null
    fun setItemClickListener(itemClickListener: OnRecyclerViewClickListener?){
        listener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val viewHolder = ViewHolder(view)

        if(listener != null){
            view.setOnClickListener { v->listener?.OnItemClickListener(v) }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.medicine.text = "Medicine: ${mData[position]}"
        holder.interval.text = "Interval: ${iData[position]}"
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}