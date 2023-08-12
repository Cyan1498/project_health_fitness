package com.grupoc.project_health_fitness.view.fragments.fitness.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.grupoc.project_health_fitness.databinding.ItemFitnessBinding
import com.grupoc.project_health_fitness.model.Fitness

class FitnessViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemFitnessBinding.bind(view)

    fun render(fitnessModel: Fitness) {
        binding.tvTitle.text = fitnessModel.title
        binding.tvDescription.text = fitnessModel.description
    }
}