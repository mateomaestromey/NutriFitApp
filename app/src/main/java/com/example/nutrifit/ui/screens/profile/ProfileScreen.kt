package com.example.nutrifit.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userName: String,
    userEmail: String,
    onLogoutClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7F2)) // Fondo cremita
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Logo NutriFit
            Image(
                painter = painterResource(id = R.drawable.nutrifit_logo),
                contentDescription = "NutriFit Logo",
                modifier = Modifier
                    .height(80.dp) // Más grande
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card Usuario
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Altura grande
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Imagen de Perfil
                    Icon(
                        painter = painterResource(id = R.drawable.logo_user),
                        contentDescription = "Foto de perfil",
                        tint = Color.Black,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = userName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF264D48)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = userEmail,
                        fontSize = 14.sp,
                        color = Color(0xFF264D48)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onLogoutClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF264D48),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Cerrar Sesión")
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Comidas Favoritas
            Text(
                text = "Comidas Favoritas",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF264D48)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card Favoritos
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // 👈 MÁS ALTA
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center, // 👈 CENTRADO VERTICAL
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nutrifit_logo),
                        contentDescription = "NutriFit Logo",
                        modifier = Modifier.height(40.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Estamos trabajando en esto\npronto lo tendrás",
                        fontSize = 16.sp,
                        color = Color.Black,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
