package com.grupoc.project_health_fitness.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
=======
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
>>>>>>> 6c7374d (patron MVVM)
import com.grupoc.project_health_fitness.R


class AlimentacionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alimentacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val añadir: AppCompatImageButton = view.findViewById(R.id.añadirAlimentacion_button)


        añadir.setOnClickListener {
            val showAgregarAlimento = AgregarAlimentoFragment()
            showAgregarAlimento.show((activity as AppCompatActivity).supportFragmentManager,"showAgregarAlimento")}

    }
}