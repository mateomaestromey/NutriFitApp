package com.example.nutrifit.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.products.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun onNombreChange(value: String) {
        _state.value = _state.value.copy(nombre = value)
    }

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun registrarUsuario(onSuccess: () -> Unit) {
        val nombre = _state.value.nombre
        val email = _state.value.email
        val password = _state.value.password

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val uid = result.user?.uid ?: return@addOnSuccessListener
                    val usuario = Usuario(uid, nombre, email)

                    firestore.collection("usuarios").document(uid).set(usuario)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener {
                            _state.value = _state.value.copy(errorMessage = "Error al guardar usuario")
                        }
                }
                .addOnFailureListener {
                    _state.value = _state.value.copy(errorMessage = "Error al registrar: ${it.message}")
                }
                .addOnCompleteListener {
                    _state.value = _state.value.copy(isLoading = false)
                }
        }
    }
}
