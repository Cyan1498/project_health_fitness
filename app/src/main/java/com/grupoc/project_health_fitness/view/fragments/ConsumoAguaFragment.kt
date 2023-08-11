package com.grupoc.project_health_fitness.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.grupoc.project_health_fitness.R


class ConsumoAguaFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_consumo_agua, container, false)
        val closeText = view.findViewById<TextView>(R.id.closeAguaText)
      closeText.setOnClickListener {
           requireFragmentManager().beginTransaction().remove(this).commit()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_añadir=view.findViewById<Button>(R.id.registrarAgua_button)
        val edit_agua=view.findViewById<EditText>(R.id.ingresarAgua_txt)
    }
}