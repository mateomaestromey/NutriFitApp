package com.example.nutrifit.ui.screens

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object NutriFitList : Screens("nutrifit_list_screen")
    object NutriFitDetail : Screens("nutrifit_detail_screen")
    object Login : Screens("login_screen")
    object ComingSoon : Screens("coming_soon_screen")
    object Profile : Screens("profile_screen") // 👈 AGREGADO
}

