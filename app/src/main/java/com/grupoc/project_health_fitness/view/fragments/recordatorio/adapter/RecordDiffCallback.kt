package com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter

import androidx.recyclerview.widget.DiffUtil
import com.grupoc.project_health_fitness.model.Recordatorio

class RecordDiffCallback(
    private val oldList: List<Recordatorio>,
    private val newList: List<Recordatorio>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
