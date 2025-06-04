package com.example.nutrifit.ui.screens.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import coil.compose.AsyncImage
import com.example.nutrifit.R
import com.example.nutrifit.products.NutriFit

@Composable
fun NutriFitUiItem(
    nutriFit: NutriFit,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .clickable {
                onClick(nutriFit.id)
            }
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = nutriFit.imagen,
                contentDescription = nutriFit.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = nutriFit.nombre ?: "Producto sin nombre",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.icono_calorias),
                        contentDescription = "Calorías",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF2C3E50)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${nutriFit.nutriments?.calorias ?: "N/A"} calorías",
                        fontSize = 14.sp,
                        color = Color(0xFF2C3E50)
                    )
                }
            }
            // Corazón (sin función por ahora)
            IconButton(
                onClick = { /* TODO: Marcar como favorito en un futuro */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo_corazon),
                    contentDescription = "Favorito",
                    modifier = Modifier.size(25.dp),
                    tint = Color(0xFF2C3E50)
                )
            }
        }
    }
}

