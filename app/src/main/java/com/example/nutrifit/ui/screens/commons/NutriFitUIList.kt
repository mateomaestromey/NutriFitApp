package com.example.nutrifit.ui.screens.commons

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import com.example.nutrifit.products.NutriFit


@Composable
fun NutriFitUiList(
    list : List<NutriFit>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
){
    LazyColumn(
        modifier = modifier
    ){
        items(
            items=list,
            key = {it -> it.id!!}
        ) {
            nutriFit ->
            NutriFitUiItem(nutriFit, onClick= onClick)
        }
    }
}