package com.grupoc.project_health_fitness.utils.validations

import android.view.KeyEvent
import com.grupoc.project_health_fitness.databinding.FragmentLoginBinding

class LoginFormValidator(private val binding: FragmentLoginBinding) {

    fun validateForm(): Boolean {
        return validateEmail() && validatePassword()
    }

    fun validateEmail(): Boolean {
        val emailPattern = Regex("[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+")
        val email = binding.edEmail.text.toString().trim()
        return if (email.isEmpty()) {
            binding.edEmailL.error = "Required"
            false
        } else if (!email.matches(emailPattern)) {
            binding.edEmailL.error = "Valid E-mail"
            false
        } else {
            binding.edEmailL.error = null
            true
        }
    }

    fun validatePassword(): Boolean {
        val password = binding.edPassword.text.toString().trim()
        return if (password.isEmpty()) {
            binding.edPasswordL.error = "Requireda"
            false
        } else if (password.length !in 6..10) {
            binding.edPasswordL.error = "¡La contraseña debe tener entre 6 y 10 caracteres!"
            false
        } else {
            binding.edPasswordL.error = null
            true
        }
    }

    fun keyEvent() {
        binding.edEmail.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_TAB && event.action == KeyEvent.ACTION_DOWN) {
                binding.edEmail.requestFocus()
                true
            } else {
                false
            }
        }
    }
}
