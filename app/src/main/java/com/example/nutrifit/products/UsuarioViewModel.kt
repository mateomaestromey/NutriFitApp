package com.example.nutrifit.ui.profile

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.nutrifit.products.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioViewModel : ViewModel() {

    var usuario by mutableStateOf(Usuario())
        private set

    init {
        cargarUsuario()
    }

    private fun cargarUsuario() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document(uid)
            .get()
            .addOnSuccessListener { result ->
                usuario = result.toObject(Usuario::class.java) ?: Usuario()
            }

    }

}
