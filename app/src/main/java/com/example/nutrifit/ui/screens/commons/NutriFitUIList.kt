package com.example.nutrifit.ui.screens.commons

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nutrifit.products.NutriFit
import com.example.nutrifit.ui.favoritos.FavoritosViewModel

@Composable
fun NutriFitUiList(
    list: List<NutriFit>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    favoritosViewModel: FavoritosViewModel
) {
    LazyColumn(modifier = modifier) {
        items(list) { producto ->
            NutriFitUiItem(
                nutriFit = producto,
                onClick = onClick,
                favoritosViewModel = favoritosViewModel
            )
        }
    }
}

