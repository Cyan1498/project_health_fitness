package com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter

import androidx.recyclerview.widget.RecyclerView
import com.grupoc.project_health_fitness.databinding.ItemRecyclerviewBinding
import com.grupoc.project_health_fitness.model.Recordatorio

class RecordViewHolder(
    private val binding: ItemRecyclerviewBinding,
    private val recordList: List<Recordatorio>,
    private val itemClickListener: OnRecordClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val recordL = recordList[position]
                itemClickListener.onRecordItemClick(recordL)
            }
        }
    }

    fun render(record: Recordatorio) {
        binding.tvRecordName.text = record.name
        binding.tvInstruction.text = record.note
//        binding.tvRecordDateInit.text = record.dateInit.toString()
        // Bind the data to your views in the layout
        // For example: binding.textViewName.text = recordatorio.name
    }
}
