package com.example.nutrifit.products.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface INutriFitDao {

    @Query("SELECT * FROM nutriFit")
    suspend fun getAll(): List<NutriFitLocal>

    @Query("SELECT * FROM nutriFit WHERE id= :id LIMIT 1")
    suspend fun findById(id : String): NutriFitLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg nutriFit: NutriFitLocal)

    @Delete
    suspend fun delete(nutriFit: NutriFitLocal)
}