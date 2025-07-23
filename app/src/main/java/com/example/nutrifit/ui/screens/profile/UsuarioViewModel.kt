package com.example.nutrifit.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.products.UsuarioDataSource
import com.example.nutrifit.products.Usuario
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.example.nutrifit.products.UsuarioRepository

class UsuarioViewModel(
    private val userRepository: UsuarioRepository = UsuarioRepository()
) : ViewModel() {

    var usuario by mutableStateOf(Usuario())
        private set

    var favoritos by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            usuario = userRepository.fetchUser()
            favoritos = userRepository.getFavorites()
        }
    }

    fun agregarAFavoritos(id: String) {
        viewModelScope.launch {
            userRepository.addFavorite(id)
            favoritos = userRepository.getFavorites()
        }
    }

    fun eliminarDeFavoritos(id: String) {
        viewModelScope.launch {
            userRepository.removeFavorite(id)
            favoritos = userRepository.getFavorites()
        }
    }

    fun esFavorito(id: String): Boolean {
        return favoritos.contains(id)
    }
}
