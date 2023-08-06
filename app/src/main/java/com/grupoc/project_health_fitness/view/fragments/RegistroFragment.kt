package com.grupoc.project_health_fitness.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentRegistroBinding

class RegistroFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //_binding = FragmentSignupBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        val btnContinuar: Button = view.findViewById(R.id.signup_button)
        btnContinuar.setOnClickListener {
            // Realizar la transacción al siguiente fragmento
            val siguienteFragmento = CrearUsuarioFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, siguienteFragmento)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()


        }

    }

}