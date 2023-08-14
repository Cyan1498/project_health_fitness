package com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.grupoc.project_health_fitness.databinding.ItemRecyclerviewBinding
import com.grupoc.project_health_fitness.model.Recordatorio

class RecordViewHolder(
    private val binding: ItemRecyclerviewBinding,
    private val recordList: List<Recordatorio>,
    private val itemClickListener: OnRecordClickListener,
//    itemDeleteListener: OnRecordClickListener,
//    recyclerView: RecyclerView // Agrega este parámetro
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val recordL = recordList[position]
                itemClickListener.onRecordItemClick(recordL)
            }

        }
//        binding.btnDelete.setOnClickListener {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                val item = recordList[position]
//                itemClickListener.onRecordDelete(item)
//            }
//        }

//        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
//            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
//                0,
//                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//            ) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    return false
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    val position = viewHolder.adapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        val record = recordList[position]
//                        itemDeleteListener.onRecordDelete(record)
//                    }
//                }
//            }
//
//            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//            itemTouchHelper.attachToRecyclerView(recyclerView)
//        }
    }

    fun bindDeleteClickListener(record: Recordatorio, position: Int) {
        binding.btnDelete.setOnClickListener {
            itemClickListener.onRecordDelete(record, position)
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
