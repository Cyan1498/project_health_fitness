package com.grupoc.project_health_fitness.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentRegistroBinding
import com.grupoc.project_health_fitness.utils.SnackbarUtils
import com.grupoc.project_health_fitness.utils.validations.RegistroFormValidator
import com.grupoc.project_health_fitness.view.activities.InicioActivity
import com.grupoc.project_health_fitness.viewmodel.UserViewModel

class RegistroFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()
    private lateinit var registroFormValidator: RegistroFormValidator
    private lateinit var loadingAnimationView: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)

        setupValidationListeners()
        loadingAnimationView = binding.lotieLoading

        binding.signupButton.setOnClickListener {
            val nameUser = binding.edName.text.toString().trim()
            val emailUser = binding.edEmail.text.toString().trim()
            val passUser = binding.edPassword.text.toString().trim()

            //loadingAnimationView.playAnimation()
            val formValidator = RegistroFormValidator(binding)
            if (formValidator.validateForm()) {
                loadingAnimationView.visibility = View.VISIBLE
                loadingAnimationView.playAnimation()
                // Ocultar el formulario y mostrar la animación de carga
                binding.contentForm.visibility = View.GONE
                viewModel.registerUser(nameUser, emailUser, passUser)
            }else {
                SnackbarUtils.showSnackbar(
                    this,
                    "Complete correctamente sus datos",
                    backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
                )
            }
        }

        viewModel.signupSuccess.observe(viewLifecycleOwner) { success ->
            //it representa el valor emitido por el LiveData observado.
            if (success) {
                SnackbarUtils.showSnackbar(
                    this,
                    "Usuario registrado con éxito",
                    backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
                )
                //viewModel.resetEvents()
                val intent = Intent(requireContext(), InicioActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        viewModel.userAlreadyRegisteredEvent.observe(viewLifecycleOwner) {
            SnackbarUtils.showSnackbar(
                this,
                "El usuario ya existe",
                backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
            )
        }

        viewModel.unknownErrorEvent.observe(viewLifecycleOwner) {
            SnackbarUtils.showSnackbar(
                this,
                "Error desconocido",
                backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
            )
        }

        return binding.root
    }

    private fun setupValidationListeners() {
        registroFormValidator = RegistroFormValidator(binding)

        binding.edName.doAfterTextChanged {
            registroFormValidator.validateName()
        }
        binding.edEmail.doAfterTextChanged {
            registroFormValidator.validateEmail()
        }
        binding.edPassword.doAfterTextChanged {
            registroFormValidator.validatePassword()
        }
        binding.edConfirmPass.doAfterTextChanged {
            registroFormValidator.validateConPassword()
        }
    }

}