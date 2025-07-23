package com.example.nutrifit.ui.screens.profile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val userEmail = user?.email ?: "Correo no disponible"
    val uid = user?.uid

    var nombreUsuario by remember { mutableStateOf("Cargando...") }

    LaunchedEffect(uid) {
        uid?.let {
            val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
            db.collection("usuarios").document(it).get()
                .addOnSuccessListener { document ->
                    nombreUsuario = document.getString("nombre") ?: "Usuario"
                }
                .addOnFailureListener {
                    nombreUsuario = "Usuario"
                }
        }
    }

    ProfileScreen(
        userName = nombreUsuario,
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
