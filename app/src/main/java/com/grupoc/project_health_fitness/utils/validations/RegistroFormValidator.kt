package com.grupoc.project_health_fitness.utils.validations

import com.grupoc.project_health_fitness.databinding.FragmentRegistroBinding

class RegistroFormValidator(private val binding: FragmentRegistroBinding) {

    fun validateForm(): Boolean {
        return validateName() && validateEmail() && validatePassword() && validateConPassword()
    }

    fun validateName(): Boolean {
        val name = binding.edName.text.toString().trim()
        return if (name.isEmpty()) {
            binding.edNameL.error = "Required"
            false
        } else {
            binding.edNameL.error = null
            true
        }
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
            binding.edPasswordL.error = "Required"
            false
        } else if (password.length !in 6..10) {
            binding.edPasswordL.error = "¡La contraseña debe tener entre 6 y 10 caracteres!"
            false
        } else {
            binding.edPasswordL.error = null
            true
        }
    }

    fun validateConPassword(): Boolean {
        val conPassword = binding.edConfirmPass.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        return if (conPassword.isEmpty()) {
            binding.edConfirmPassL.error = "Required"
            false
        } else if (password.length !in 6..10) {
            binding.edConfirmPassL.error = "Password must be 6 to 10 Characters!"
            false
        } else if (conPassword != password) {
            binding.edConfirmPassL.error = "Password Don't Match!"
            false
        } else {
            binding.edConfirmPassL.error = null
            true
        }
    }

}
