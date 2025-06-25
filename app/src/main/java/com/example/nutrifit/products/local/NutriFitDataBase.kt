package com.example.nutrifit.products.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Database(entities = [NutriFitLocal::class], version = 1)
abstract class NutriFitDataBase : RoomDatabase() {

    abstract fun nutriFitDao(): INutriFitDao

    companion object {

        @Volatile
        private var _instance: NutriFitDataBase? = null

        fun getInstance(context: Context): NutriFitDataBase = _instance ?: synchronized(this){
            _instance ?: buildDataBase(context)
        }

        private fun buildDataBase(context: Context): NutriFitDataBase =
            Room.databaseBuilder(context, NutriFitDataBase::class.java, "nutriFit_database")
                .fallbackToDestructiveMigration()
                .build()
    }

    suspend fun clean(context: Context) = coroutineScope {
        launch(Dispatchers.IO) {
            getInstance(context).clearAllTables()
        }
    }

}