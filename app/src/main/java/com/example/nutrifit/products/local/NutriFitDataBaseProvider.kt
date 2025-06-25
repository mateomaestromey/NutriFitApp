package com.example.nutrifit.products.local

import android.content.Context

object NutriFitDataBaseProvider {
    lateinit var dbLocal: NutriFitDataBase
        private set

    fun createDataBase(context: Context){
        dbLocal= NutriFitDataBase.getInstance(context)
    }

}