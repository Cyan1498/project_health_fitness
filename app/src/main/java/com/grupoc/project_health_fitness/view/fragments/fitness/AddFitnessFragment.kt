package com.grupoc.project_health_fitness.view.fragments.fitness

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentAddFitnessBinding
import com.grupoc.project_health_fitness.model.Fitness

class AddFitnessFragment : Fragment() {

    private var _binding: FragmentAddFitnessBinding? = null
    private val binding get() = _binding!!
    private lateinit var et_title: EditText
    private lateinit var et_description: EditText
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddFitnessBinding.inflate(inflater, container, false)

        db = FirebaseFirestore.getInstance()
        et_title = binding.etTitle
        et_description = binding.etDescription

        binding.saveFitness.setOnClickListener {
            saveFitness()
        }

        return binding.root
    }

    private fun saveFitness() {
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Por favor, ingrese title o description",
                Toast.LENGTH_SHORT
            ).show()
            return
        } else {
            val fitnessRef: CollectionReference = db.collection("excercises")
            val newFitness = Fitness(title, description)
            fitnessRef.add(newFitness)
            Toast.makeText(requireContext(), "Exercise added", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}