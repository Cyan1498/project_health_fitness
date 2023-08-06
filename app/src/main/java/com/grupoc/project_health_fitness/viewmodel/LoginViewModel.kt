package com.grupoc.project_health_fitness.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _userNotFoundEvent = MutableLiveData<Boolean>()
    val userNotFoundEvent: LiveData<Boolean> get() = _userNotFoundEvent

    private val _incorrectPasswordEvent = MutableLiveData<Boolean>()
    val incorrectPasswordEvent: LiveData<Boolean> get() = _incorrectPasswordEvent

    private val _unknownErrorEvent = MutableLiveData<Boolean>()
    val unknownErrorEvent: LiveData<Boolean> get() = _unknownErrorEvent

    // LiveData para mostrar/ocultar el ProgressDialog
    private val _showProgressDialog = MutableLiveData<Boolean>()
    val showProgressDialog: LiveData<Boolean> get() = _showProgressDialog


    fun loginUser(email: String, password: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Mostrar el ProgressDialog
            _showProgressDialog.value = true
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    // Ocultar el ProgressDialog
                    _showProgressDialog.value = false
                    if (task.isSuccessful) {
                        // Inicio de sesión exitoso, puedes establecer la variable de LiveData success
                        _loginResult.value = true
                    } else {
                        // Hubo un problema en el inicio de sesión, verifica el error
                        when (task.exception) {
                            is FirebaseAuthInvalidUserException -> {
                                _userNotFoundEvent.value = true // Usuario no existe
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                _incorrectPasswordEvent.value = true // Contraseña incorrecta
                            }
                            else -> {
                                _unknownErrorEvent.value = true // Otro error desconocido
                            }
                        }
                    }
                }
        } else {
            _loginResult.value = false
        }
    }

}
