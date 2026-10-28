package com.example.kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.data.LatestHistory
import com.example.kotlin.data.MedDB
import com.example.kotlin.data.Medicine
import com.example.kotlin.data.MedicineHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Adaptor(var context: Context, var mData: List<Medicine>) : RecyclerView.Adapter<ViewHolder>(){

    private var listener: OnRecyclerViewClickListener? = null
    private val historyDao = MedDB.getDatabase(context).historyDao()
    fun setItemClickListener(itemClickListener: OnRecyclerViewClickListener?){
        listener = itemClickListener
    }

    private var onTakenClick: ((Medicine) -> Unit)? = null

    fun setTakenClickListener(
        listener: (Medicine) -> Unit
    ) {
        onTakenClick = listener
    }

    fun updateData(newData: List<Medicine>) {
        mData = newData
        notifyDataSetChanged()
    }

    private var latestHistory: List<LatestHistory> = emptyList()

    fun updateLatestHistory(newHistory: List<LatestHistory>) {
        latestHistory = newHistory
        notifyDataSetChanged()
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
        val medicine = mData[position]
        holder.medicine.text = "Medicine: ${medicine.medName}"
        holder.interval.text = "Interval: ${medicine.time}"

        val history = latestHistory.find {
            it.medicine_id == medicine.id
        }
        if (history != null) {
            holder.date.text = SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
            ).format(Date(history.taken_at))
        } else {
            holder.date.text = "No record"
        }

        holder.taken.setOnClickListener {
            onTakenClick?.invoke(medicine)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}