package com.grupoc.project_health_fitness.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.grupoc.project_health_fitness.R


class ConsumoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_consumo, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onConsumoAgua:Button=view.findViewById(R.id.consumoAgua_button)
        val onAlimentacion:Button=view.findViewById(R.id.alimentacion_button)

        onConsumoAgua.setOnClickListener {
            val showConsumoAgua = ConsumoAguaFragment()
            showConsumoAgua.show((activity as AppCompatActivity).supportFragmentManager,"showAgregarAlimento")
        }

       // onAlimentacion.setOnClickListener {
        //    Log.d("ConsumoFragment", "Botón de Alimentación presionado")
        //    val showAlimentacion = AlimentacionFragment()
         ////   fragmentTransaction.replace(R.id.consumoFragment, showAlimentacion)
         //   fragmentTransaction.addToBackStack(null)
          //  fragmentTransaction.commit()
       // }

        onAlimentacion.setOnClickListener {
            findNavController().navigate(R.id.alimentacionFragment)
        }

    }




}