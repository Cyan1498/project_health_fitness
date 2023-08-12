package com.grupoc.project_health_fitness.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentAlimentacionBinding
import java.util.Calendar


class AlimentacionFragment : Fragment() {

    private lateinit var binding: FragmentAlimentacionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlimentacionBinding.inflate(layoutInflater)

        binding.dtpickerAlimentacion.setOnClickListener {onScheduledDate() }
        binding.timePickerAlimentacion.setOnClickListener { onScheduledTime() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val añadir: AppCompatImageButton = view.findViewById(R.id.añadirAlimentacion_button)


        añadir.setOnClickListener {
            val showAgregarAlimento = AgregarAlimentoFragment()
            showAgregarAlimento.show(
                (activity as AppCompatActivity).supportFragmentManager,
                "showAgregarAlimento"
            )
        }

    }

    private fun onScheduledDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { datepicker, y, m, d ->
            val formattedDay = if (d <= 9) "0$d" else "$d"
            val formattedMonth = if (m < 9) "0${m + 1}" else "${m + 1}"
            val formattedDate = "$formattedDay/$formattedMonth/$y"

            binding.dtpickerAlimentacion.setText(formattedDate)

        }
        DatePickerDialog(requireContext(), listener, year, month, day).show()
    }

    private fun onScheduledTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val listener = TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
            val formattedHour = if (h <= 9) "0$h" else "$h"
            val formattedMinute = if (m <= 9) "0$m" else "$m"
            val formattedTime = "$formattedHour:$formattedMinute"

            binding.timePickerAlimentacion.setText(formattedTime)
        }

        TimePickerDialog(requireContext(), listener, hour, minute, true).show()
    }
}