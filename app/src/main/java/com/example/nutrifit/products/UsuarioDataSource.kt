package com.example.nutrifit.products

import com.example.nutrifit.products.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UsuarioDataSource {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun getUser(): Usuario {
        val uid = auth.currentUser?.uid ?: throw Exception("Usuario no autenticado")
        val docRef = db.collection("usuarios").document(uid)
        val doc = docRef.get().await()

        return if (doc.exists()) {
            doc.toObject(Usuario::class.java) ?: Usuario(uid = uid)
        } else {
            val nuevoUsuario = Usuario(uid = uid)
            docRef.set(nuevoUsuario).await()
            nuevoUsuario
        }
    }

    suspend fun getFavorites(): List<String> {
        return getUser().favoritos
    }

    suspend fun addFavorite(id: String) {
        val usuario = getUser()
        if (!usuario.favoritos.contains(id)) {
            usuario.favoritos.add(id)
            db.collection("usuarios").document(usuario.uid).set(usuario).await()
        }
    }

    suspend fun removeFavorite(id: String) {
        val usuario = getUser()
        if (usuario.favoritos.contains(id)) {
            usuario.favoritos.remove(id)
            db.collection("usuarios").document(usuario.uid).set(usuario).await()
        }
    }
}
