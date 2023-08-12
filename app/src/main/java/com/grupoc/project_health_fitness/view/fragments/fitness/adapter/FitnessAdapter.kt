package com.grupoc.project_health_fitness.view.fragments.fitness.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.model.Fitness

class FitnessAdapter(private val fitnessList: ArrayList<Fitness>) :
    RecyclerView.Adapter<FitnessViewHolder>() {
    //class FitnessAdapter(private val fitnessList: FirestoreRecyclerOptions<Fitness>) :
//    FirestoreRecyclerAdapter<Fitness, FitnessViewHolder>(fitnessList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FitnessViewHolder(layoutInflater.inflate(R.layout.item_fitness, parent, false))
    }
    override fun getItemCount(): Int {
        return fitnessList.size
    }

    override fun onBindViewHolder(holder: FitnessViewHolder, position: Int) {
        val item = fitnessList[position]
        holder.render(item)
    }

    fun deleteItem(position: Int) {
        if (position >= 0 && position < fitnessList.size) {
            val removedItem = fitnessList.removeAt(position)
            notifyDataSetChanged()

//            val db = FirebaseFirestore.getInstance().
//            val documentId = removedItem.
        }
    }
}