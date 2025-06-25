package com.example.nutrifit.products

data class Usuario(
    val uid: String = "",
    val nombre: String = "",
    val email: String = "",
    val favoritos: MutableList<String> = mutableListOf()
)
