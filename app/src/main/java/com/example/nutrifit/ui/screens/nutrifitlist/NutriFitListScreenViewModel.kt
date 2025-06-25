package com.example.nutrifit.ui.screens.nutrifitlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.domain.INutriFitRepository
import com.example.nutrifit.products.NutriFit
import com.example.nutrifit.products.NutriFitRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class NutriFitListScreenViewModel(
    private val nutriFitRepository: INutriFitRepository = NutriFitRepository()
) : ViewModel() {
    var uiState by mutableStateOf(NutriFitListScreenState())
        private set

    private var fetchJob: Job? = null
    private var initialList: List<NutriFit> = emptyList()

    init {
        fetchInitialNutriFits()
    }

    private fun fetchInitialNutriFits() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true) // 👈 Loading empieza
                val list = nutriFitRepository.fetchNutriFits("")
                initialList = list.take(10)
                uiState = uiState.copy(nutriFitList = initialList, isLoading = false) // 👈 Loading termina
            } catch (e: IOException) {
                Log.e("NutriFitApp", "Error cargando lista inicial")
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun fetchNutriFit() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true) // 👈 Loading empieza
                if (uiState.searchQuery.isBlank()) {
                    uiState = uiState.copy(nutriFitList = initialList, isLoading = false)
                } else {
                    val searchResults = nutriFitRepository.fetchNutriFits(uiState.searchQuery)
                    uiState = uiState.copy(nutriFitList = searchResults, isLoading = false)
                }
            } catch (e: IOException) {
                Log.e("NutriFitApp", "Error en la lista de alimentos")
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun searchChange(search: String) {
        uiState = uiState.copy(searchQuery = search)
        fetchNutriFit()
    }

    fun agregarAFavoritos(producto: NutriFit) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let {
            db.collection("usuarios")
                .document(it)
                .collection("favoritos")
                .document(producto.id)
                .set(producto)
        }
    }

}