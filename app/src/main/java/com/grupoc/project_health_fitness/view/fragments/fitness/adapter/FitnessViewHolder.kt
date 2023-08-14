package com.grupoc.project_health_fitness.view.fragments.fitness.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grupoc.project_health_fitness.databinding.ItemFitnessBinding
import com.grupoc.project_health_fitness.databinding.ItemRecyclerviewBinding
import com.grupoc.project_health_fitness.model.Fitness
import com.grupoc.project_health_fitness.model.Recordatorio
import com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter.OnRecordClickListener

//class FitnessViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    private val binding = ItemFitnessBinding.bind(view)
//
//    fun render(fitnessModel: Fitness) {
//        binding.tvCategory.text = fitnessModel.category
//        binding.tvRep.text = fitnessModel.reps
//        binding.tvRest.text = fitnessModel.rest
//        binding.tvSeries.text = fitnessModel.series
//    }
//}
class FitnessViewHolder(
    private val binding: ItemFitnessBinding

//    private val recordList: List<Fitness>
//    private val itemClickListener: OnRecordClickListener
) : RecyclerView.ViewHolder(binding.root) {

//    init {
//        binding.root.setOnClickListener {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                val recordL = recordList[position]
//                itemClickListener.onRecordItemClick(recordL)
//            }
//        }
//    }

    fun render(fitness: Fitness) {
        binding.tvCategory.text = fitness.category
        binding.tvRep.text = fitness.reps
        binding.tvRest.text = fitness.rest
        binding.tvSeries.text = fitness.series
//        binding.tvRecordDateInit.text = record.dateInit.toString()
        // Bind the data to your views in the layout
        // For example: binding.textViewName.text = recordatorio.name
    }
}