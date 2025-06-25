package com.example.nutrifit.ui.screens.nutrifitlist

import com.example.nutrifit.products.NutriFit

data class NutriFitListScreenState(
    val nutriFitList: List<NutriFit> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val username : String = ""
)
