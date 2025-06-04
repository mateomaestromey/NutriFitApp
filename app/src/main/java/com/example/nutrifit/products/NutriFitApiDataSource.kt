package com.example.nutrifit.products

import android.util.Log
import okio.IOException
import retrofit2.HttpException

class NutriFitApiDataSource : INutriFitDataSource{

    private val TAG = "NutriFitApp"

    override suspend fun getNutriFitList(search : String): List<NutriFit> {
        Log.d(TAG, "NutriFitApiDataSource.getNutriFitResult")

        return try {
            Log.d(TAG, "NutriFitApiDataSource.getNutriFitResult Result: ${search} ")
            val nutriFitResult = RetrofitInstance.nutriFitApi.getNutriFitSearch(search)
            Log.d(TAG, "NutriFitApiDataSource.getNutriFitResult Result: ${nutriFitResult.products.size} ")
            return nutriFitResult.products
        } catch (e: HttpException){
            Log.e(TAG, "Error desconocido: ${e.code()} ${e.message()}")
            emptyList()
        } catch (e: IOException){
            Log.e(TAG, "Error de Network: ${e.localizedMessage}")
            emptyList()
        } catch (e: Exception){
            Log.e(TAG, "Error desconocido: ${e.localizedMessage}")
            emptyList()
        }



    }

    override suspend fun getNutriFitById(nutriFitId: String): NutriFit {
        return RetrofitInstance.nutriFitApi.getNutriFit(nutriFitId).product
    }
}