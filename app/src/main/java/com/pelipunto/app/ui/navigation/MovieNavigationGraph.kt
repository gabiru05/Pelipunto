package com.pelipunto.app.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.pelipunto.app.auth.AuthViewModel
import com.pelipunto.app.ui.detail.CastListScreen
import com.pelipunto.app.ui.detail.MovieDetailScreen
import com.pelipunto.app.ui.detail.ReviewsListScreen
import com.pelipunto.app.ui.home.HomeScreen
import com.pelipunto.app.ui.settings.SettingsScreen
import com.pelipunto.app.utils.K

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val screensInBottomBar = listOf(
        Screen.Home,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val shouldShowBottomBar = screensInBottomBar.any { it.route == currentDestination?.route }

            if (shouldShowBottomBar) {
                AppBottomBar(navController = navController, items = screensInBottomBar)
            }
        },
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (screensInBottomBar.any { it.route == currentDestination?.route }) {
                TopAppBar(
                    title = { Text("PeliPunto") },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier,
            enterTransition = { fadeIn() + scaleIn() },
            exitTransition = { fadeOut() + shrinkOut() }
        ) {
            composable(route = Screen.Home.route) {
                HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    onMovieClick = { movieId ->
                        navController.navigate("film/$movieId")
                    }
                )
            }

            composable(
                route = "film/{${K.MOVIE_ID}}",
                arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt(K.MOVIE_ID) ?: 0
                MovieDetailScreen(
                    onNavigateUp = { navController.navigateUp() },
                    onMovieClick = { newId ->
                        navController.navigate("film/$newId") {
                            launchSingleTop = true
                        }
                    },
                    onActorClick = {},
                    onSeeAllCastClick = {
                        // Navega a la lista de elenco, reemplazando el placeholder por el ID
                        val route = Screen.CastList.route.replace("{${K.MOVIE_ID}}", "$movieId")
                        navController.navigate(route)
                    },
                    onSeeAllReviewsClick = {
                        // Navega a la lista de reseñas, reemplazando el placeholder por el ID
                        val route = Screen.ReviewsList.route.replace("{${K.MOVIE_ID}}", "$movieId")
                        navController.navigate(route)
                    }
                )
            }

            composable(route = Screen.Settings.route) {
                val authViewModel: AuthViewModel = hiltViewModel()
                val context = LocalContext.current
                val user = authViewModel.authState.collectAsState().value.user
                SettingsScreen(
                    modifier = Modifier.padding(innerPadding),
                    onLogoutClicked = {
                        authViewModel.logout(context)
                    },
                    userName = user?.displayName ?: user?.email?.substringBefore("@") ?: "",
                    userEmail = user?.email ?: ""
                )
            }

            // --- NUEVAS PANTALLAS ---
            composable(
                route = Screen.CastList.route,
                arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
            ) {
                CastListScreen(onNavigateUp = { navController.navigateUp() })
            }

            composable(
                route = Screen.ReviewsList.route,
                arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
            ) {
                ReviewsListScreen(onNavigateUp = { navController.navigateUp() })
            }
        }
    }
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Inicio", Icons.Default.Home)
    object Settings : Screen("settings", "Ajustes", Icons.Default.Settings)
    // --- NUEVAS RUTAS CON ARGUMENTOS ---
    object CastList : Screen("cast_list/{${K.MOVIE_ID}}", "Elenco", Icons.Default.Home)
    object ReviewsList : Screen("reviews_list/{${K.MOVIE_ID}}", "Reseñas", Icons.Default.Home)
}

@Composable
private fun AppBottomBar(navController: NavHostController, items: List<Screen>) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}