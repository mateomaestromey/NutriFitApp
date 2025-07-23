package com.example.nutrifit.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrifit.R
import com.example.nutrifit.products.ConsejosDataSource
import com.example.nutrifit.ui.favoritos.FavoritosViewModel
import com.example.nutrifit.ui.screens.commons.NutriFitUiList

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userName: String,
    userEmail: String,
    onLogoutClick: () -> Unit,
    favoritosViewModel: FavoritosViewModel = viewModel()
) {
    val listaFavoritos by favoritosViewModel.productosFavoritos.collectAsState()
    val isLoading by favoritosViewModel.isLoading.collectAsState()
    val scrollState = rememberScrollState()

    var consejoActual by remember {
        mutableStateOf("Presioná el botón para recibir tu consejo de salud y empezar a cuidarte 😋🍓")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7F2))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nutrifit_logo),
                contentDescription = "NutriFit Logo",
                modifier = Modifier.height(80.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo_user),
                        contentDescription = "Foto de perfil",
                        tint = Color.Black,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = userName, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF264D48))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = userEmail, fontSize = 14.sp, color = Color(0xFF264D48))
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onLogoutClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF264D48), contentColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Cerrar Sesión")
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Alimentos Favoritos",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF264D48)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                when {
                    isLoading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    listaFavoritos.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.nutrifit_logo),
                                contentDescription = "NutriFit Logo",
                                modifier = Modifier.height(40.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Aún no guardaste ningún alimento",
                                fontSize = 16.sp,
                                color = Color.Black,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    else -> {
                        NutriFitUiList(
                            list = listaFavoritos,
                            modifier = Modifier.fillMaxSize(),
                            onClick = {},
                            favoritosViewModel = favoritosViewModel
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Consejo Aleatorio",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF264D48)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text(
                        text = consejoActual,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    consejoActual = ConsejosDataSource.random()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF264D48),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Nuevo Consejo")
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
