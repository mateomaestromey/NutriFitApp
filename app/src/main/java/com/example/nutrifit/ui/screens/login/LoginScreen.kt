package com.example.nutrifit.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutrifit.R

@Composable
fun LoginScreen(
    onGoogleLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController

){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAF9F6) // Fondo general crema claro
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 32.dp)
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.nutrifit_logo),
                contentDescription = "NutriFit Logo",
                modifier = Modifier
                    .width(300.dp)
                    .padding(bottom = 0.dp) // Más pegado
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Título
            Text(
                text = "Iniciar Sesión",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2D5B50)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Usuario
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Nombre de usuario") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Contraseña") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Olvidaste contraseña
            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color(0xFF2D5B50),
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Iniciar Sesión
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D5B50)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Iniciar Sesión", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Registrarse
            Text(
                text = "Registrarse",
                color = Color(0xFF2D5B50),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Google
            OutlinedButton(
                onClick = onGoogleLoginClick,
                border = BorderStroke(1.dp, Color(0xFF2D5B50)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo_google), // Icono de Google
                        contentDescription = "Google Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Continuar con Google",
                        color = Color(0xFF2D5B50),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

}