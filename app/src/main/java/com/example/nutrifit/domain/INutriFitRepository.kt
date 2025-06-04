package com.example.nutrifit.domain

import com.example.nutrifit.products.NutriFit

interface INutriFitRepository {
    suspend fun fetchNutriFits(search : String) : List<NutriFit>
    suspend fun fetchNutriFit(nutriFitId : String) : NutriFit
}