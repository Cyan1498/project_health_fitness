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

class AgregarAlimentoFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_agregar_alimento, container, false)
        val closeText = view.findViewById<TextView>(R.id.closeText)
        closeText.setOnClickListener {
            requireFragmentManager().beginTransaction().remove(this).commit()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_Añadir = view.findViewById<Button>(R.id.registrarAlimento_button)
        val edit_ingAlim=view.findViewById<EditText>(R.id.ingresarAlimento_txt)
        val edit_cal=view.findViewById<EditText>(R.id.ingresarCalorias_txt)
        val edit_gras=view.findViewById<EditText>(R.id.ingresarGrasa_txt)
        val edit_carb=view.findViewById<EditText>(R.id.ingresarCarbohidratos_txt)
        val edit_prot=view.findViewById<EditText>(R.id.ingresarProteina_txt)


    }
}