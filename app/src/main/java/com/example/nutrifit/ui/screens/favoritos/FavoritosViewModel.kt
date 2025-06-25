package com.example.nutrifit.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.products.NutriFit
import com.example.nutrifit.products.NutriFitRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritosViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _favoritos = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val favoritos: StateFlow<Map<String, Boolean>> = _favoritos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarFavoritos()
    }

    private fun cargarFavoritos() {
        val uid = auth.currentUser?.uid ?: return
        _isLoading.value = true
        firestore.collection("usuarios").document(uid).get()
            .addOnSuccessListener { doc ->
                val lista = doc.get("favoritos") as? List<String> ?: emptyList()
                _favoritos.value = lista.associateWith { true }
                _isLoading.value = false
            }
    }

    fun isFavorite(productId: String): StateFlow<Boolean> {
        return favoritos.map { it[productId] == true }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    }

    fun addToFavorites(productId: String) {
        val uid = auth.currentUser?.uid ?: return
        val docRef = firestore.collection("usuarios").document(uid)

        docRef.get().addOnSuccessListener { doc ->
            val actuales = doc.get("favoritos") as? List<String> ?: emptyList()
            val nuevos = (actuales + productId).distinct()
            _favoritos.value = nuevos.associateWith { true }
            docRef.update("favoritos", nuevos)
                .addOnFailureListener {
                    docRef.set(mapOf("favoritos" to nuevos))
                }
        }
    }

    fun removeFromFavorites(productId: String) {
        val uid = auth.currentUser?.uid ?: return
        val docRef = firestore.collection("usuarios").document(uid)

        docRef.get().addOnSuccessListener { doc ->
            val actuales = doc.get("favoritos") as? List<String> ?: emptyList()
            val nuevos = actuales.filterNot { it == productId }
            _favoritos.value = nuevos.associateWith { true }
            docRef.update("favoritos", nuevos)
                .addOnFailureListener {
                    docRef.set(mapOf("favoritos" to nuevos))
                }
        }
    }

    fun getProductosFavoritos(callback: (List<NutriFit>) -> Unit) {
        val uid = auth.currentUser?.uid ?: return
        _isLoading.value = true
        firestore.collection("usuarios").document(uid).get()
            .addOnSuccessListener { doc ->
                val listaIds = doc.get("favoritos") as? List<String> ?: emptyList()

                if (listaIds.isEmpty()) {
                    _isLoading.value = false
                    callback(emptyList())
                    return@addOnSuccessListener
                }

                firestore.collection("Favoritos")
                    .whereIn("id", listaIds)
                    .get()
                    .addOnSuccessListener { result ->
                        val encontrados = result.mapNotNull { it.toObject(NutriFit::class.java) }
                        val encontradosIds = encontrados.map { it.id }

                        val faltantes = listaIds.filter { it !in encontradosIds }

                        if (faltantes.isEmpty()) {
                            _isLoading.value = false
                            callback(encontrados)
                        } else {
                            val repository = NutriFitRepository()
                            viewModelScope.launch {
                                val nuevos = faltantes.mapNotNull {
                                    try {
                                        val prod = repository.fetchNutriFit(it)
                                        firestore.collection("Favoritos").document(it).set(prod)
                                        prod
                                    } catch (e: Exception) {
                                        null
                                    }
                                }
                                _isLoading.value = false
                                callback(encontrados + nuevos)
                            }
                        }
                    }
            }
    }
}