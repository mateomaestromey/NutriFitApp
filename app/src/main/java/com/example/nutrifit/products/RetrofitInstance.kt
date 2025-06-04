package com.example.nutrifit.products

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val BASE_URL = "https://world.openfoodfacts.org/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val nutriFitApi : INutriFitAPI by lazy{
        retrofit.create(INutriFitAPI::class.java)
    }
}