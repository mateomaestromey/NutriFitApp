package com.example.nutrifit.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nutrifit.R
import com.example.nutrifit.ui.screens.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    // Espera 2 segundos y navega
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Screens.Login.route) {
            popUpTo("splash") { inclusive = true }
        }
    }

    // UI del Splash
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF264D48)), // 👈 Fondo VERDE como en tu diseño
        contentAlignment = Alignment.Center
    ) {
        // Solo el LOGO
        Image(
            painter = painterResource(id = R.drawable.logo_splash),
            contentDescription = "Logo NutriFit",
            modifier = Modifier.size(300.dp) // 👈 Cambiá el tamaño si querés más grande o más chico
        )
    }
}
