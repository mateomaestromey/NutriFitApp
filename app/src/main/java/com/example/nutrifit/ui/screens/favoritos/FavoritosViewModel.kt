package com.example.nutrifit.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.products.UsuarioRepository
import com.example.nutrifit.products.NutriFit
import com.example.nutrifit.products.NutriFitRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritosViewModel(
    private val userRepository: UsuarioRepository = UsuarioRepository(),
    private val nutriFitRepository: NutriFitRepository = NutriFitRepository()
) : ViewModel() {

    private val _productosFavoritos = MutableStateFlow<List<NutriFit>>(emptyList())
    val productosFavoritos: StateFlow<List<NutriFit>> = _productosFavoritos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        cargarFavoritos()
    }

    fun cargarFavoritos() {
        viewModelScope.launch {
            _isLoading.value = true

            val ids = userRepository.getFavorites()
            val encontrados = mutableListOf<NutriFit>()

            for (id in ids) {
                try {
                    val prod = nutriFitRepository.fetchNutriFit(id)
                    encontrados.add(prod)
                } catch (_: Exception) {}
            }

            _productosFavoritos.value = encontrados
            _isLoading.value = false
        }
    }

    fun eliminarDeFavoritos(id: String) {
        viewModelScope.launch {
            userRepository.removeFavorite(id)
            cargarFavoritos()
        }
    }

    fun agregarAFavoritos(id: String) {
        viewModelScope.launch {
            userRepository.addFavorite(id)
            cargarFavoritos()
        }
    }

    // ✅ Esta versión es observable
    fun esFavorito(id: String): StateFlow<Boolean> {
        return productosFavoritos
            .map { list -> list.any { it.id == id } }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                false
            )
    }
}
