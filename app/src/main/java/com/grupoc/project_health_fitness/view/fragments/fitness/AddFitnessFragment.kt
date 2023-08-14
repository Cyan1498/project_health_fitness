package com.grupoc.project_health_fitness.view.fragments.fitness

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentAddFitnessBinding
import com.grupoc.project_health_fitness.model.Fitness
import com.grupoc.project_health_fitness.utils.SnackbarUtils
import com.grupoc.project_health_fitness.viewmodel.FitnessViewModel

class AddFitnessFragment : Fragment() {

    private var _binding: FragmentAddFitnessBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FitnessViewModel by viewModels()
    private lateinit var et_rep: EditText
    private lateinit var et_category: EditText
    private lateinit var et_rest: EditText
    private lateinit var et_series: EditText
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddFitnessBinding.inflate(inflater, container, false)

//        db = FirebaseFirestore.getInstance()

        saveFitness()
        dropdownCategoria()
        observerData()

        return binding.root
    }

    private fun saveFitness() {
        val selectedFitness = arguments?.getParcelable<Fitness>("fitnessToEdit")
        if (selectedFitness != null) {
            showFitnessData(selectedFitness)
        }

        binding.saveFitness.setOnClickListener {
            val rep = binding.etReps.text.toString()
            val category = binding.autoCompletecCategoryExcersise.text.toString()
            val rest = binding.etRest.text.toString()
            val series = binding.etSeries.text.toString()

//            if (rep.trim().isEmpty() || category.trim().isEmpty() || rest.trim()
//                    .isEmpty() || series.trim().isEmpty()
//            ) {
//                Toast.makeText(
//                    requireContext(),
//                    "Por favor, completar las casillas",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
////            val fitnessRef: CollectionReference = db.collection("excercises")
////            val newFitness = Fitness(category, rep, rest, series)
////            fitnessRef.add(newFitness)
////            Toast.makeText(requireContext(), "Ejercicio agregado correctamente", Toast.LENGTH_SHORT)
////                .show()
////            findNavController().popBackStack()
//                val fitnessId = selectedFitness?.id
//                if (fitnessId != null) {
//
//                } else {
                    viewModel.registerFitness(category, rep, rest, series)
////                    viewModel.updateFitness(fitnessId, category, rep, rest, series)
//                    Toast.makeText(
//                        requireContext(),
//                        "Ejercicio agregado correctamente",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                    findNavController().popBackStack()
//                }
//            }
        }
    }

    private fun observerData() {
        viewModel.signupSuccess.observe(viewLifecycleOwner) { success ->
            //it representa el valor emitido por el LiveData observado.
            if (success) {
//                loadingAnimationView.visibility = View.GONE
                SnackbarUtils.showSnackbar(
                    this,
                    "Usuario registrado con éxito",
                    backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
                )
                //viewModel.resetEvents()
                // Cerrar el fragmento después de que la animación se haya detenido
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
//        viewModel.updateSuccess.observe(viewLifecycleOwner) { success ->
//            if (success) {
//                SnackbarUtils.showSnackbar(
//                    this,
//                    "Usuario Actualizado con éxito",
//                    backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
//                )
//                //viewModel.resetEvents()
//                // Cerrar el fragmento después de que la animación se haya detenido
//                requireActivity().supportFragmentManager.popBackStack()
//            }
//        }
//        viewModel.unknownErrorEvent.observe(viewLifecycleOwner) {
//            SnackbarUtils.showSnackbar(
//                this,
//                "Error al Registrar",
//                backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
//            )
//        }
//        viewModel.getAllRecordatorios().observe(viewLifecycleOwner) { recordList ->
//            val sortedList = recordList.sortedByDescending { it.createdAt }
//            recordAdapter.updateData(sortedList)
//        }
    }

    private fun showFitnessData(selectedFitness: Fitness) {
        binding.autoCompletecCategoryExcersise.setText(selectedFitness.category)
        binding.etReps.setText(selectedFitness.reps)
        binding.etRest.setText(selectedFitness.rest)
        binding.etSeries.setText(selectedFitness.series)
    }

    private fun dropdownCategoria() {
        val category = resources.getStringArray(R.array.categoria)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, category)
        binding.autoCompletecCategoryExcersise.setAdapter(arrayAdapter)
    }
}