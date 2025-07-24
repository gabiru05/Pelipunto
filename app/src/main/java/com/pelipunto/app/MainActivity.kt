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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetMovieTheme {
                App()
            }
        }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()
        val authViewModel: AuthViewModel = hiltViewModel()
        val authState = authViewModel.authState.collectAsState().value
        val isAuthenticated = authState.user != null

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
                    }
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