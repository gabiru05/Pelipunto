package com.pelipunto.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.pelipunto.app.ui.home.HomeScreen
import com.pelipunto.app.ui.navigation.MovieNavigationGraph
import com.pelipunto.app.ui.theme.JetMovieTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.pelipunto.app.auth.AuthViewModel
import com.pelipunto.app.ui.navigation.AuthNavigationGraph
import androidx.compose.runtime.collectAsState
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private var onGoogleToken: ((String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configuración de Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            JetMovieTheme {
                App()
            }
        }
    }

    // Launcher para el intent de Google Sign-In
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data: Intent? = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                onGoogleToken?.invoke(idToken)
            }
        } catch (e: Exception) {
            // Manejo de error: podrías mostrar un Snackbar global si lo deseas
        }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()
        val authViewModel: AuthViewModel = hiltViewModel()
        val authState = authViewModel.authState.collectAsState().value
        val isAuthenticated = authState.user != null

        // Callback para lanzar Google Sign-In desde Compose
        val launchGoogleSignIn: (onToken: (String) -> Unit) -> Unit = { onToken ->
            onGoogleToken = onToken
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }

        if (isAuthenticated) {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                MovieNavigationGraph(
                    navController = navController,
                    modifier = Modifier.padding(it)
                )
            }
        } else {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                AuthNavigationGraph(
                    navController = navController,
                    modifier = Modifier.padding(it),
                    onAuthSuccess = {
                        // Al autenticarse, se reconstruye el Composable y se muestra MovieNavigationGraph
                    },
                    onGoogleSignIn = { onToken -> launchGoogleSignIn(onToken) },
                    authViewModel = authViewModel
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetMovieTheme {
        Greeting("Android")
    }
}