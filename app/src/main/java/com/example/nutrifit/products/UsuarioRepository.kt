package com.example.nutrifit.products

import com.example.nutrifit.products.UsuarioDataSource
import com.example.nutrifit.products.Usuario

class UsuarioRepository(
    private val dataSource: UsuarioDataSource = UsuarioDataSource()
) {
    suspend fun fetchUser(): Usuario = dataSource.getUser()
    suspend fun getFavorites(): List<String> = dataSource.getFavorites()
    suspend fun addFavorite(id: String) = dataSource.addFavorite(id)
    suspend fun removeFavorite(id: String) = dataSource.removeFavorite(id)
}
