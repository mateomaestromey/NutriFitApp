package com.example.nutrifit.ui.screens.nutrifitdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nutrifit.R

@Composable
fun NutriFitDetailScreen(
    nutriFitId: String,
    modifier: Modifier = Modifier,
    vm: NutriFitDetailViewModel = viewModel()
) {
    vm.setNutriFitId(nutriFitId)

    if (vm.uiState.nutriFitDetail.id.isNullOrEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F7F2)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF204E4A))
        }
    } else {
        val product = vm.uiState.nutriFitDetail

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F7F2))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo arriba
            Image(
                painter = painterResource(id = R.drawable.logo_nutrifit),
                contentDescription = "NutriFit Logo",
                modifier = Modifier.height(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Imagen grande
            AsyncImage(
                model = product.imagen ?: "",
                contentDescription = product.nombre ?: "Producto sin nombre",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card de información
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Nombre
                    Text(
                        text = product.nombre ?: "Sin nombre",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF204E4A)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Información nutricional
                    NutritionalInfoRow(
                        icon = R.drawable.icono_calorias,
                        label = "${product.nutriments?.calorias?.toInt() ?: 0} calorías"
                    )
                    NutritionalInfoRow(
                        icon = R.drawable.icono_proteina,
                        label = "${product.nutriments?.proteinas?.toInt() ?: 0} gramos de proteína"
                    )
                    NutritionalInfoRow(
                        icon = R.drawable.icono_carbohidratos,
                        label = "${product.nutriments?.carbohidratos?.toInt() ?: 0} gramos de carbs"
                    )
                    NutritionalInfoRow(
                        icon = R.drawable.icono_grasas,
                        label = "${product.nutriments?.grasas?.toInt() ?: 0} gramos de grasas"
                    )
                }
            }
        }
    }
}

@Composable
fun NutritionalInfoRow(icon: Int, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color(0xFF204E4A)
        )
    }
}
