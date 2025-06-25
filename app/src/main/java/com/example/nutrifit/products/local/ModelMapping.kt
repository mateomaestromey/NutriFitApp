package com.example.nutrifit.products.local

import com.example.nutrifit.products.NutriFit
import com.example.nutrifit.products.Nutriments

fun NutriFit.toLocal() = NutriFitLocal(
    id,
    nombre,
    imagen,
    nutriments.calorias,
    nutriments.proteinas,
    nutriments.carbohidratos,
    nutriments.grasas
)

fun List<NutriFit>.toLocal() = map(NutriFit::toLocal)

fun NutriFitLocal.toExternal() = NutriFit(
    id,
    nombre,
    imagen,
    Nutriments(
        calorias,
        proteinas,
        carbohidratos,
        grasas
    )

)

fun List<NutriFitLocal>.localToExternal() = map(NutriFitLocal::toExternal)