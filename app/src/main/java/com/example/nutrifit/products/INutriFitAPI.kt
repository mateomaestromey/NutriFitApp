package com.example.nutrifit.products

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface INutriFitAPI {
    @GET("cgi/search.pl")
    suspend fun getNutriFitSearch(
        @Query("search_terms") search: String,
        @Query("search_simple") searchSimple: Int = 1,
        @Query("action") action: String = "process",
        @Query("json") json: Int = 1,
        @Query("page_size") pageSize : Int = 5
    ) : NutriFitResult

    @GET("api/v0/product/{code}.json")
    suspend fun getNutriFit
                (@Path("code") nutriFitId: String)
    : NutriFitDetailResult

}