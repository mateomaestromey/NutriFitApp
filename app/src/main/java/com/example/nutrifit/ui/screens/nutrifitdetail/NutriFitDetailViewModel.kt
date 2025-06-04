package com.example.nutrifit.ui.screens.nutrifitdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifit.domain.INutriFitRepository
import com.example.nutrifit.products.NutriFitRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NutriFitDetailViewModel(
    private val nutriFitRepository : INutriFitRepository = NutriFitRepository()
) : ViewModel() {
    var uiState by mutableStateOf(NutriFitDetailScreenState())
        private set

    private var fetchJob: Job? = null

    fun fetchNutriFit(){
        fetchJob?.cancel()
        fetchJob= viewModelScope.launch {
            uiState = uiState.copy(nutriFitId = uiState.nutriFitId, nutriFitDetail = nutriFitRepository.fetchNutriFit(uiState.nutriFitId))
        }
    }

    fun setNutriFitId(nutriFitId: String): Unit{
        uiState = uiState.copy(nutriFitId = nutriFitId, nutriFitDetail = uiState.nutriFitDetail)
        fetchNutriFit()
    }


}