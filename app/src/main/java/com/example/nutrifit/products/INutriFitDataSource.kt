package com.example.nutrifit.products

interface INutriFitDataSource {

    suspend fun getNutriFitList(search : String) : List<NutriFit>
    suspend fun getNutriFitById(nutriFitId : String) : NutriFit
}