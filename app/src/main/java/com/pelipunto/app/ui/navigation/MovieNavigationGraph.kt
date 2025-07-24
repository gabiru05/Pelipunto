package com.pelipunto.app.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        startDestination = Route.HomeScreen().route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(
            route = Route.HomeScreen().route,
            enterTransition = { fadeIn() + scaleIn() },
            exitTransition = { fadeOut() + shrinkOut() }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen(
                    onMovieClick = {
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = it)
                        ) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                        }
                    }
                )
                FloatingBottomBar(
                    onHomeClick = {
                        navController.navigate(Route.HomeScreen().route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                        }
                    },
                    onSettingsClick = {
                        navController.navigate(Route.SettingsScreen().route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                        }
                    }
                )
            }
        }

        composable(
            route = Route.FilmScreen().routeWithArgs,
            arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MovieDetailScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onMovieClick = {
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = it)
                        ) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                        }
                    },
                    onActorClick = {}
                )
                FloatingBottomBar(
                    onHomeClick = {
                        navController.navigate(Route.HomeScreen().route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                        }
                    },
                    onSettingsClick = {
                        // TODO: Navegación a ajustes si existe
                    }
                )
            }
        }

        composable(
            route = Route.SettingsScreen().route
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                SettingsScreen()
                FloatingBottomBar(
                    onHomeClick = {
                        navController.navigate(Route.HomeScreen().route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                        }
                    },
                    onSettingsClick = {
                        // Ya estás en ajustes
                    }
                )
            }
        }
    }

}