package com.example.nutrifit.products.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nutrifit.products.Nutriments
import com.google.gson.annotations.SerializedName


@Entity("nutriFit")
data class NutriFitLocal (
    @PrimaryKey val id : String = "",
    val nombre: String = "",
    val imagen : String = "",
    val calorias: Float = 0f,
    val proteinas: Float = 0f,
    val carbohidratos: Float = 0f,
    val grasas: Float = 0f
)