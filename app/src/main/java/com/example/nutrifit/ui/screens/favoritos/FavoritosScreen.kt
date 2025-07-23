package com.example.nutrifit.ui.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.nutrifit.products.NutriFit
import com.example.nutrifit.ui.screens.Screens
import com.example.nutrifit.ui.screens.commons.NutriFitUiList
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavoritosScreen(
    favoritosViewModel: FavoritosViewModel = viewModel(),
    navController: NavHostController
) {
    val listaFavoritos by favoritosViewModel.productosFavoritos.collectAsState()
    val isLoading by favoritosViewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7F2))
            .padding(16.dp)
    ) {
        Text(
            text = "Tus Favoritos",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C3E50)
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            listaFavoritos.isEmpty() -> {
                Text("Aún no guardaste ningún alimento")
            }

            else -> {
                NutriFitUiList(
                    list = listaFavoritos,
                    modifier = Modifier.fillMaxSize(),
                    onClick = { id -> navController.navigate(Screens.NutriFitDetail.route + "/$id") },
                    favoritosViewModel = favoritosViewModel
                )
            }
        }
    }
}
