package com.grupoc.project_health_fitness.view.fragments.fitness.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.ItemFitnessBinding
import com.grupoc.project_health_fitness.databinding.ItemRecyclerviewBinding
import com.grupoc.project_health_fitness.model.Fitness
import com.grupoc.project_health_fitness.model.Recordatorio
import com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter.OnRecordClickListener
import com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter.RecordDiffCallback
import com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter.RecordViewHolder

//class FitnessAdapter(private val fitnessList: ArrayList<Fitness>) :
//    RecyclerView.Adapter<FitnessViewHolder>() {
//    //class FitnessAdapter(private val fitnessList: FirestoreRecyclerOptions<Fitness>) :
////    FirestoreRecyclerAdapter<Fitness, FitnessViewHolder>(fitnessList) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        return FitnessViewHolder(layoutInflater.inflate(R.layout.item_fitness, parent, false))
//    }
//
//    override fun getItemCount(): Int {
//        return fitnessList.size
//    }
//
//    override fun onBindViewHolder(holder: FitnessViewHolder, position: Int) {
//        val item = fitnessList[position]
//        holder.render(item)
//    }
//
//    fun deleteItem(position: Int) {
//        if (position >= 0 && position < fitnessList.size) {
//            val removedItem = fitnessList.removeAt(position)
//            notifyDataSetChanged()
//
////            val db = FirebaseFirestore.getInstance().
////            val documentId = removedItem.
//        }
//    }
//}
class FitnessAdapter(
    private var fitnessList: List<Fitness>
) : RecyclerView.Adapter<FitnessViewHolder>() {

    private val fitnessL: MutableList<Fitness> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessViewHolder {
        val binding =
            ItemFitnessBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FitnessViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FitnessViewHolder, position: Int) {
        val item = fitnessList[position]
//        holder.itemView.setOnClickListener {
//            itemClickListener.onRecordItemClick(item)
//        }
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return fitnessList.size
    }

        fun updateData(newList: List<Fitness>) {
        fitnessList = newList
        notifyDataSetChanged()
    }

    fun getFitnessAtPosition(position: Int): Fitness {
        return fitnessList[position]
    }


//    fun updateData(newList: List<Fitness>) {
//        val diffResult = DiffUtil.calculateDiff(RecordDiffCallback(fitnessList, newList))
//        fitnessList = newList
//        diffResult.dispatchUpdatesTo(this)
//    }
}