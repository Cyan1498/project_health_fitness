package com.grupoc.project_health_fitness.view.fragments.recordatorio

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentRecordFormBinding
import com.grupoc.project_health_fitness.view.activities.InicioActivity
import java.util.Calendar

class RecordFormFragment : Fragment() {

    private var _binding: FragmentRecordFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordFormBinding.inflate(inflater, container, false)
        binding.dtpicker.setOnClickListener { onScheduledDate() }
        dropdownInstrucciones()
        dropdownPastillas()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Resto de tu código

        (activity as InicioActivity).setShowBackArrow(true)
        (activity as InicioActivity).setBottomNavigationVisible(false)
        (activity as InicioActivity).setDrawerEnabled(false)

        // Resto de tu código
    }
    private fun onScheduledDate() {
        val selectedCalendar = Calendar.getInstance()
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { datepicker, y, m, d ->
            val formattedDay = if (d <= 9) "0$d" else "$d"
            val formattedMonth = if (m < 9) "0${m + 1}" else "${m + 1}"
            val formattedDate = "$formattedDay/$formattedMonth/$y"
            binding.dtpicker.setText(formattedDate)
        }
        DatePickerDialog(requireContext(), listener, year, month, dayOfMonth).show()
    }

    private fun dropdownInstrucciones() {
        val instruccion = resources.getStringArray(R.array.instrucciones)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, instruccion)
        binding.autoComInstruciones.setAdapter(arrayAdapter)
    }

    private fun dropdownPastillas() {
        val pastilla = resources.getStringArray(R.array.pastillas)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, pastilla)
        binding.autoCompPastilla.setAdapter(arrayAdapter)
    }

}