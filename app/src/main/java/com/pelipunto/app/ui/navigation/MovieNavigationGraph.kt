// RUTA: app/src/main/java/com/pelipunto/app/ui/navigation/MovieNavigationGraph.kt
package com.pelipunto.app.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pelipunto.app.auth.AuthViewModel
import com.pelipunto.app.ui.components.FloatingBottomBar
import com.pelipunto.app.ui.detail.MovieDetailScreen
import com.pelipunto.app.ui.home.HomeScreen
import com.pelipunto.app.ui.settings.SettingsScreen
import com.pelipunto.app.utils.K

@Composable
fun MovieNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = "home", // Ruta de inicio
        modifier = modifier.fillMaxSize()
    ) {

        composable(
            route = "home",
            enterTransition = { fadeIn() + scaleIn() },
            exitTransition = { fadeOut() + shrinkOut() }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen(
                    onMovieClick = { movieId ->
                        navController.navigate("film/$movieId")
                    }
                )
                FloatingBottomBar(
                    onHomeClick = { /* Ya estás en Home */ },
                    onSettingsClick = {
                        navController.navigate("settings") {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }


        composable(
            route = "film/{${K.MOVIE_ID}}",
            arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MovieDetailScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onMovieClick = { movieId ->
                        navController.navigate("film/$movieId") {
                            launchSingleTop = true
                        }
                    },
                    onActorClick = {}
                )
                FloatingBottomBar(
                    onHomeClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onSettingsClick = {
                        navController.navigate("settings") {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }


        composable(
            route = "settings"
        ) {
            val authViewModel: AuthViewModel = hiltViewModel()
            val context = LocalContext.current

            Box(modifier = Modifier.fillMaxSize()) {
                SettingsScreen(
                    onLogoutClicked = {
                        // cierra la sesión de Google y Firebase
                        authViewModel.logout(context)
                    }
                )
                FloatingBottomBar(
                    onHomeClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onSettingsClick = { }
                )
            }
        }
    }
}