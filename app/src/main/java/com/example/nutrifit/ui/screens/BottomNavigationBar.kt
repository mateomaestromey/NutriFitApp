package com.example.nutrifit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nutrifit.R

data class BottomNavItem(val label: String, val route: String, val iconResId: Int)

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Screens.NutriFitList.route, R.drawable.logo_hoja),
        BottomNavItem("Favorites", Screens.Favoritos.route, R.drawable.logo_favs),
        BottomNavItem("Profile", Screens.Profile.route, R.drawable.logo_user) // 👈 Ahora va a Profile
    )

    NavigationBar(
        containerColor = Color(0xFF264D48),
        tonalElevation = 0.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = currentRoute?.startsWith(item.route) == true

            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.label,
                        modifier = Modifier.size(40.dp), // Podés agregar tamaño si querés
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                            if (selected) Color.Black else Color.White
                        )
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
