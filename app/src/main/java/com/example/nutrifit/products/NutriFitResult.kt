package com.example.nutrifit.products

import com.google.gson.annotations.SerializedName

data class NutriFitResult (
    val products: List<NutriFit>
)

data class NutriFitDetailResult(
    val product: NutriFit
)

data class NutriFit (
    @SerializedName("code")
    val id : String,
    @SerializedName("product_name_es")
    val nombre: String,
    @SerializedName("image_url")
    val imagen : String,

    val nutriments : Nutriments

)

data class Nutriments(
    @SerializedName("energy-kcal_100g")
    val calorias : Float,

    @SerializedName("proteins_100g")
    val proteinas : Float,

    @SerializedName("carbohydrates_100g")
    val carbohidratos : Float,

    @SerializedName("fat_100g")
    val grasas : Float
)

fun emptyNutriFit() : NutriFit{
    return NutriFit("","","", Nutriments(0f,0f,0f,0f))
}
