package com.example.nutrifit.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.products.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var email: String = ""
    var password: String = ""

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser != null) {
            viewModelScope.launch {
                _uiEvent.send("loginOK")
            }
        }
    }

    fun loginWithEmail() {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val docRef = firestore.collection("usuarios").document(user.uid)
                            docRef.get().addOnSuccessListener { snapshot ->
                                val nombre = snapshot.getString("nombre") ?: "Usuario"
                                val usuario = Usuario(
                                    uid = user.uid,
                                    nombre = nombre,
                                    email = user.email ?: "",
                                    favoritos = (snapshot.get("favoritos") as? List<String>)?.toMutableList() ?: mutableListOf()
                                )

                                // Guardalo en donde necesites (ViewModel global, etc.)

                                viewModelScope.launch {
                                    _uiEvent.send("loginOK")
                                }
                            }
                        }
                    } else {
                        viewModelScope.launch {
                            _uiEvent.send("error:${task.exception?.localizedMessage ?: "Error desconocido"}")
                        }
                    }
                }
        }
    }
}
