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
import androidx.compose.runtime.collectAsState
import com.pelipunto.app.auth.LogoutTestScreen

@Composable
fun AuthNavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onAuthSuccess: () -> Unit,
    onGoogleSignIn: ((String) -> Unit) -> Unit,
    authViewModel: AuthViewModel
) {
    val viewModel = authViewModel
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
                onRegisterClick = { navController.navigate("register") },
                isLoggedIn = false,
                onLogout = { }
            )
        }
        composable("login") {
            LoginScreen(
                onLoginClick = { email, password -> viewModel.loginWithEmail(email, password) },
                onGoogleClick = {
                    onGoogleSignIn { idToken ->
                        viewModel.loginWithGoogle(idToken)
                    }
                },
                onBack = { navController.popBackStack() },
                isLoading = state.result is AuthResult.Loading,
                errorMessage = (state.result as? AuthResult.Error)?.message
            )
            LaunchedEffect(state.result) {
                if (state.result is AuthResult.Success) {
                    navController.navigate("logoutTest") {
                        popUpTo("welcome") { inclusive = true }
                    }
                    viewModel.clearResult()
                }
            }
        }
        composable("register") {
            RegisterScreen(
                onRegisterClick = { name, email, password -> viewModel.registerWithEmail(name, email, password) },
                onGoogleClick = {
                    onGoogleSignIn { idToken ->
                        viewModel.loginWithGoogle(idToken)
                    }
                },
                onBack = { navController.popBackStack() },
                isLoading = state.result is AuthResult.Loading,
                errorMessage = (state.result as? AuthResult.Error)?.message
            )
            LaunchedEffect(state.result) {
                if (state.result is AuthResult.Success) {
                    navController.navigate("logoutTest") {
                        popUpTo("welcome") { inclusive = true }
                    }
                    viewModel.clearResult()
                }
            }
        }
        composable("logoutTest") {
            LogoutTestScreen(
                onLogout = { viewModel.logout() }
            )
        }
    }
} 