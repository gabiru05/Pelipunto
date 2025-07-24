package com.pelipunto.app.ui.navigation

import com.pelipunto.app.utils.K

sealed class Route {
    data class HomeScreen(val route: String = "homeScreen") : Route()
    data class FilmScreen(
        val route: String = "FilmScreen",
        val routeWithArgs: String = "$route/{${K.MOVIE_ID}}",
    ) : Route() {
        fun getRouteWithArgs(id: Int): String {
            return "$route/$id"
        }
    }
    data class SettingsScreen(val route: String = "settingsScreen") : Route()
}