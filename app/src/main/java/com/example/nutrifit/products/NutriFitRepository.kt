package com.example.nutrifit.products

import com.example.nutrifit.domain.INutriFitRepository

class NutriFitRepository(
    val nutriFitDataSource : INutriFitDataSource = NutriFitApiDataSource()
) : INutriFitRepository
{
    override suspend fun fetchNutriFits(search : String) : List<NutriFit> {
        return nutriFitDataSource.getNutriFitList(search)
    }

    override suspend fun fetchNutriFit(nutriFitId: String): NutriFit {
        return nutriFitDataSource.getNutriFitById(nutriFitId)
    }
}