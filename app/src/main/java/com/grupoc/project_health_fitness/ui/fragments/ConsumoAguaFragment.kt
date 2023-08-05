package com.grupoc.project_health_fitness.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.grupoc.project_health_fitness.R


class ConsumoAguaFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_consumo_agua, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_añadir=view.findViewById<Button>(R.id.registrarAgua_button)
        val edit_agua=view.findViewById<EditText>(R.id.ingresarAgua_txt)
    }
}