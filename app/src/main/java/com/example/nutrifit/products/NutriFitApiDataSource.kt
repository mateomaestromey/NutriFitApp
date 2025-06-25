package com.example.nutrifit.products

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import okio.IOException
import retrofit2.HttpException
import kotlinx.coroutines.tasks.await

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


        Log.d("NUTRIFITDB", "getNutriFitById")

        val db = FirebaseFirestore.getInstance()

        var nutriFitResult = db.collection("Favoritos").document(nutriFitId.toString()).get().await()
        var nutriFit = nutriFitResult.toObject(NutriFit::class.java)
        if (nutriFit != null) {
            Log.d("NUTRIFITDB", "encontrado en Firestore")
            return nutriFit
        }
        else {
            Log.d("NUTRIFITDB", "no encontrado en Firestore")
            nutriFit = RetrofitInstance.nutriFitApi.getNutriFit(nutriFitId).product
            db.collection("Favoritos").document(nutriFitId.toString()).set(nutriFit)
            return nutriFit
        }
    }
}