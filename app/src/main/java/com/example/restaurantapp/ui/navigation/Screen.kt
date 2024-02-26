package com.example.restaurantapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object DetailRestaurant : Screen("home/{id}") {
        fun createRoute(id: String) = "home/$id"
    }
}