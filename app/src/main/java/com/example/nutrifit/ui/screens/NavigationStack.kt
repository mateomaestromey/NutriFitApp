package com.example.nutrifit.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutrifit.ui.screens.login.LoginScreen
import com.example.nutrifit.ui.screens.nutrifitdetail.NutriFitDetailScreen
import com.example.nutrifit.ui.screens.nutrifitlist.NutriFitListScreen
import com.example.nutrifit.ui.screens.splash.SplashScreen
import com.example.nutrifit.ui.screens.comingsoon.ComingSoonScreen

import com.example.nutrifit.ui.screens.profile.ProfileScreen

@Composable
fun NavigationStack(
    onGoogleLoginClick: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route,
        modifier = modifier // 👈 Pasarlo acá
    ) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screens.Login.route){
            LoginScreen(onGoogleLoginClick, navController = navController)
        }
        composable(route = Screens.NutriFitList.route) {
            NutriFitListScreen(navController = navController)
        }
        composable(route = Screens.NutriFitDetail.route + "/{code}") { backStackEntry ->
            val nutriFitId = backStackEntry.arguments?.getString("code")
            NutriFitDetailScreen(nutriFitId = nutriFitId ?: "")
        }
        composable(route = Screens.ComingSoon.route) {
            ComingSoonScreen()
        }
        composable(route = Screens.Profile.route) { // 👈 NUEVO
            ProfileScreen(
                onLogoutClick = {
                    // Esto te puede llevar al Login cuando cierre sesión
                    navController.navigate(Screens.Login.route) {
                        popUpTo(0) // Limpia la pila
                    }
                }
            )
        }
    }
}
