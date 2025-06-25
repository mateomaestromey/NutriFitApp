package com.example.nutrifit.ui.screens.profile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.nutrifit.ui.screens.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileRoute(navController: NavHostController) {
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: "Usuario"
    val userEmail = user?.email ?: "Correo no disponible"

    ProfileScreen(
        userName = userName,
        userEmail = userEmail,
        onLogoutClick = {
            logout(context, navController)
        }
    )
}

private fun logout(context: Context, navController: NavHostController) {
    FirebaseAuth.getInstance().signOut()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(com.example.nutrifit.R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    googleSignInClient.signOut().addOnCompleteListener {
        navController.navigate(Screens.Login.route) {
            popUpTo(0)
        }
    }
}
