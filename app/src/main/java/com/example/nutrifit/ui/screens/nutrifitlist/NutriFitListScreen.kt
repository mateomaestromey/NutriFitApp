package com.example.nutrifit.ui.screens.nutrifitlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.nutrifit.R
import com.example.nutrifit.ui.favoritos.FavoritosViewModel
import com.example.nutrifit.ui.screens.Screens
import com.example.nutrifit.ui.screens.commons.NutriFitUiList

@Composable
fun NutriFitListScreen(
    modifier: Modifier = Modifier,
    vm: NutriFitListScreenViewModel = viewModel(),
    navController: NavHostController,
    favoritosViewModel: FavoritosViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9F6)) // fondo crema
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_nutrifit),
                contentDescription = "Logo NutriFit",
                modifier = Modifier.height(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(0.dp))

        SearchBarWithButton(
            query = vm.uiState.searchQuery,
            onQueryChanged = { vm.searchChange(it) },
            onSearchClicked = { vm.fetchNutriFit() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Alimentos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C3E50),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (vm.uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF264D48))
            }
        } else {
            NutriFitUiList(
                list = vm.uiState.nutriFitList,
                modifier = Modifier.fillMaxSize(),
                onClick = { id -> navController.navigate(Screens.NutriFitDetail.route + "/$id") },
                favoritosViewModel = favoritosViewModel
            )
        }
    }
}

@Composable
fun SearchBarWithButton(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color.Gray,
                    modifier = Modifier.padding(start = 12.dp)
                )
                BasicTextField(
                    value = query,
                    onValueChange = { onQueryChanged(it) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 12.dp),
                    decorationBox = { innerTextField ->
                        if (query.isEmpty()) {
                            Text(text = "Buscar...", color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { onSearchClicked() },
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C3E50))
        ) {
            Text(text = "Buscar", color = Color.White)
        }
    }
}
