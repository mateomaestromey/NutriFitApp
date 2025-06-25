package com.example.nutrifit.products

import android.util.Log
import com.example.nutrifit.products.local.NutriFitDataBase
import com.example.nutrifit.products.local.NutriFitDataBaseProvider
import com.example.nutrifit.products.local.toExternal
import com.example.nutrifit.products.local.toLocal
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
        val dbLocal = NutriFitDataBaseProvider.dbLocal

        var nutriFitLocal = dbLocal.nutriFitDao().findById(nutriFitId)
        if (nutriFitLocal != null){
            Log.d("NUTRIFITDB", "encontrado en Room")
            return nutriFitLocal.toExternal()
        }

        var nutriFitResult = db.collection("Favoritos").document(nutriFitId.toString()).get().await()
        var nutriFit = nutriFitResult.toObject(NutriFit::class.java)
        if (nutriFit != null) {
            Log.d("NUTRIFITDB", "encontrado en Firestore")

            val nutriFitLocal = nutriFit.toLocal()
            dbLocal.nutriFitDao().insert(nutriFitLocal)

            return nutriFit
        }
        else {
            Log.d("NUTRIFITDB", "no encontrado en Firestore")
            nutriFit = RetrofitInstance.nutriFitApi.getNutriFit(nutriFitId).product
            db.collection("Favoritos").document(nutriFitId.toString()).set(nutriFit)

            val nutriFitLocal = nutriFit.toLocal()
            dbLocal.nutriFitDao().insert(nutriFitLocal)

            return nutriFit
        }
    }
}