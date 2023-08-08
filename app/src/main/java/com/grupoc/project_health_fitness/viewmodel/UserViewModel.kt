package com.grupoc.project_health_fitness.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.grupoc.project_health_fitness.model.User

class UserViewModel : ViewModel() {

    private val _signupSuccess = MutableLiveData<Boolean>()
    val signupSuccess: LiveData<Boolean> get() = _signupSuccess

    private val _userAlreadyRegisteredEvent = MutableLiveData<Boolean>()
    val userAlreadyRegisteredEvent: LiveData<Boolean> get() = _userAlreadyRegisteredEvent

    private val _unknownErrorEvent = MutableLiveData<Boolean>()
    val unknownErrorEvent: LiveData<Boolean> get() = _unknownErrorEvent

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    fun registerUser(name: String, email: String, password: String) {
        // Verificamos si el usuario ya está registrado
        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods
                    if (!signInMethods.isNullOrEmpty()) {
                        // El usuario ya está registrado, muestra el evento correspondiente.
                        _userAlreadyRegisteredEvent.value = true
                    } else {
                        // El usuario no está registrado, procede con el registro.
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val currentUser = firebaseAuth.currentUser
                                    currentUser?.let {
                                        val user = User(
                                            id = it.uid,
                                            name = name,
                                            email = email,
                                            password = password
                                        )
                                        addUserToFirestore(user)
                                    }
                                } else {
                                    // Error desconocido durante el registro
                                    _unknownErrorEvent.value = true
                                }
                            }
                    }
                } else {
                    // Error al verificar la existencia del usuario
                    _unknownErrorEvent.value = true
                }
            }
    }

    private fun addUserToFirestore(user: User) {
        firestore.collection("users").document(user.id!!)
            .set(user)
            .addOnSuccessListener {
                _signupSuccess.value = true
            }
            .addOnFailureListener {
                _unknownErrorEvent.value = true
            }
    }

}