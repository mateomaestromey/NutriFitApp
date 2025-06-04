package com.example.nutrifit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutrifit.ui.screens.BottomNavigationBar
import com.example.nutrifit.ui.screens.NavigationStack
import com.example.nutrifit.ui.screens.Screens
import com.example.nutrifit.ui.theme.NutriFitTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController: androidx.navigation.NavHostController

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            navController.navigate(Screens.NutriFitList.route) {
                                popUpTo(Screens.Login.route) { inclusive = true }
                            }
                        }
                    }
            } catch (e: ApiException) {
                Log.e("AUTH", "Error en Google Sign-In", e)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )

        setContent {
            navController = rememberNavController()

            NutriFitTheme {
                val hideBottomBarRoutes = listOf(
                    Screens.Splash.route,
                    Screens.Login.route
                )
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry.value?.destination?.route
                val showBottomBar = currentRoute !in hideBottomBarRoutes

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(navController) // ✅ Solo en pantallas que queremos
                        }
                    }
                ) { innerPadding ->
                    NavigationStack(
                        onGoogleLoginClick = {
                            launcher.launch(googleSignInClient.signInIntent)
                        },
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
