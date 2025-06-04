package com.example.nutrifit.ui.screens.comingsoon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R

@Composable
fun ComingSoonScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7F2)), // 👈 Fondo color cremita
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.nutrifit_logo),
                contentDescription = "NutriFit Logo",
                modifier = Modifier
                    .height(100.dp)
                    .padding(bottom = 24.dp)
            )
            // Texto
            Text(
                text = "Estamos trabajando en esto,\npronto lo podrás usar!",
                color = Color(0xFF264D48), // Verde oscuro
                fontSize = 18.sp,
                lineHeight = 24.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
