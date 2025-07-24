package com.pelipunto.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pelipunto.app.auth.AuthViewModel
import com.pelipunto.app.auth.AuthOptionsScreen
import com.pelipunto.app.auth.LoginScreen
import com.pelipunto.app.auth.RegisterScreen
import com.pelipunto.app.ui.welcome.WelcomeScreen
import com.pelipunto.app.auth.AuthResult

@Composable
fun AuthNavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onAuthSuccess: () -> Unit
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val state by viewModel.authState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "welcome",
        modifier = modifier
    ) {
        composable("welcome") {
            WelcomeScreen(
                onContinue = { navController.navigate("authOptions") }
            )
        }
        composable("authOptions") {
            AuthOptionsScreen(
                onLoginClick = { navController.navigate("login") },
                onRegisterClick = { navController.navigate("register") }
            )
        }
        composable("login") {
            LoginScreen(
                onLoginClick = { email, password -> viewModel.loginWithEmail(email, password) },
                onGoogleClick = { /* TODO: Google Sign-In */ },
                onBack = { navController.popBackStack() },
                isLoading = state.result is AuthResult.Loading,
                errorMessage = (state.result as? AuthResult.Error)?.message
            )
            LaunchedEffect(state.result) {
                if (state.result is AuthResult.Success) {
                    onAuthSuccess()
                    viewModel.clearResult()
                }
            }
        }
        composable("register") {
            RegisterScreen(
                onRegisterClick = { name, email, password -> viewModel.registerWithEmail(name, email, password) },
                onGoogleClick = { /* TODO: Google Sign-In */ },
                onBack = { navController.popBackStack() },
                isLoading = state.result is AuthResult.Loading,
                errorMessage = (state.result as? AuthResult.Error)?.message
            )
            LaunchedEffect(state.result) {
                if (state.result is AuthResult.Success) {
                    onAuthSuccess()
                    viewModel.clearResult()
                }
            }
        }
    }
} 