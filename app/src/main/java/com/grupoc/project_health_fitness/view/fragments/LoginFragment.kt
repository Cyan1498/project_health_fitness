package com.grupoc.project_health_fitness.view.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.grupoc.project_health_fitness.databinding.FragmentLoginBinding
import com.grupoc.project_health_fitness.utils.ProgressDialogCustom
import com.grupoc.project_health_fitness.utils.SnackbarUtils
import com.grupoc.project_health_fitness.utils.validations.LoginFormValidator
import com.grupoc.project_health_fitness.view.activities.InicioActivity
import com.grupoc.project_health_fitness.viewmodel.LoginViewModel

//import com.tashila.pleasewait.PleaseWaitDialog

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var loginFormValidator: LoginFormValidator
    private var progressDialog: ProgressDialog? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var progressDialogCustom: ProgressDialogCustom
    //private lateinit var waitDialog: PleaseWaitDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupLoginButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Es el lugar donde puedes interactuar con la vista inflada después de que se ha creado
        super.onViewCreated(view, savedInstanceState)
        // Solicitar el enfoque del EditText con un pequeño retraso
        Handler(Looper.getMainLooper()).postDelayed({
            binding.edEmail.requestFocus()
        }, 100)
        //loginFormValidator.keyEvent()
        // progressBar = binding.progressBar
        progressDialogCustom = ProgressDialogCustom(requireContext())
        //waitDialog = PleaseWaitDialog(requireContext())
        setupValidationListeners()
        observerLoginViewModel()
    }

    private fun setupValidationListeners() {
        //Esta función permite escuchar los cambios en el texto del TextInputEditText
        //y ejecutar el código que se proporciona como lambda después de que el texto ha cambiado.
        loginFormValidator = LoginFormValidator(binding)

        binding.edEmail.doAfterTextChanged {
            loginFormValidator.validateEmail()
        }

        binding.edPassword.doAfterTextChanged {
            loginFormValidator.validatePassword()
        }
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            loginFormValidator = LoginFormValidator(binding)
            if (loginFormValidator.validateForm()) {
                val email = binding.edEmail.text.toString().trim()
                val password = binding.edPassword.text.toString().trim()
                viewModel.loginUser(email, password)
            } else {
                SnackbarUtils.showSnackbar(
                    this,
                    "Ingrese correctamente sus credenciales",
                    backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
                )
            }
        }
    }

    private fun observerLoginViewModel() {

        viewModel.showProgressDialog.observe(viewLifecycleOwner, Observer { show ->
            //val progressDialog = ProgressDialogCustom(requireContext())
            if (show) {
                //waitDialog.show()
                //waitDialog.setMessage("Loadding")
                progressDialogCustom.show()

                // Mostrar el ProgressDialog
                //showProgressDialog()
                // Mostrar el ProgressBar
                //progressBar.visibility = View.VISIBLE
            } else {
                //waitDialog.dismiss()
                progressDialogCustom.dismiss()
                // Ocultar el ProgressDialog
                //hideProgressDialog()
                // Ocultar el ProgressBar
                //progressBar.visibility = View.GONE
            }
        })
        //Cuando se produce un cambio en el valor de loginResult, el fragmento recibe la notificación
        // y toma una acción basada en el resultado del inicio de sesión:
        viewModel.loginResult.observe(viewLifecycleOwner, Observer { success ->
            //viewLifecycleOwner: ciclo de vida asociado con la vista del fragmento
            //observa LiveData y evita posibles fugas de memoria cuando se registra un observador.
            if (success) {
                SnackbarUtils.showSnackbar(
                    this,
                    "Inicio de sesion exitos",
                    backgroundColor = SnackbarUtils.SnackbarColors.GREEN
                )
                requireActivity().finish()
                val intent = Intent(context, InicioActivity::class.java)
                startActivity(intent)
            }
        })

        viewModel.userNotFoundEvent.observe(viewLifecycleOwner, Observer {
            SnackbarUtils.showSnackbar(
                this,
                "Usuario no existe",
                backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
            )
        })

        viewModel.incorrectPasswordEvent.observe(viewLifecycleOwner, Observer {
            SnackbarUtils.showSnackbar(
                this,
                "Contraseña incorrecta",
                backgroundColor = SnackbarUtils.SnackbarColors.RED
            )
        })

        viewModel.unknownErrorEvent.observe(viewLifecycleOwner, Observer {
            //SnackbarUtils.showSnackbar(this, "Error desconocido")
            SnackbarUtils.showSnackbar(
                this,
                "Revise su conexion a internet",
                backgroundColor = SnackbarUtils.SnackbarColors.RED
            )
        })
    }

    //Se llama cuando la pantalla se muestra al usuario.
    override fun onResume() {
        val editText = binding.edEmail
        super.onResume()
        // Solicitar el enfoque del EditText cuando el usuario regresa a la pestaña
        editText.requestFocus()
    }

    //Se llama cuando la pantalla se cierra o desaparece.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(requireContext())
            progressDialog?.setMessage("Cargando...") // Mensaje que se muestra en el ProgressDialog
            progressDialog?.setCancelable(false)
        }
        progressDialog?.show()
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }
}

