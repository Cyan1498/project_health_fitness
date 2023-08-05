package com.grupoc.project_health_fitness.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentCrearUsuarioBinding
import java.util.Calendar

class CrearUsuarioFragment : Fragment() {
    private var _binding: FragmentCrearUsuarioBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val genero = resources.getStringArray(R.array.genero)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genero)
        binding.autoCompleteGenero.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrearUsuarioBinding.inflate(inflater, container, false)
        binding.etdate.setOnClickListener{
            showDatePicker()
        }
        return binding.root
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etdate.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

}