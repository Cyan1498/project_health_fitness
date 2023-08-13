package com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grupoc.project_health_fitness.databinding.ItemRecyclerviewBinding
import com.grupoc.project_health_fitness.model.Recordatorio

class RecordAdapter(
    private var recordList: List<Recordatorio>,
    private val itemClickListener: OnRecordClickListener
) : RecyclerView.Adapter<RecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordViewHolder(binding, recordList, itemClickListener)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val item = recordList[position]
        holder.itemView.setOnClickListener {
            itemClickListener.onRecordItemClick(item)
        }
        holder.render(item)
    }

    override fun getItemCount(): Int = recordList.size

//    fun updateData(newList: List<Recordatorio>) {
//        recordList = newList
//        notifyDataSetChanged()
//    }
    fun updateData(newList: List<Recordatorio>) {
        val diffResult = DiffUtil.calculateDiff(RecordDiffCallback(recordList, newList))
        recordList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}
