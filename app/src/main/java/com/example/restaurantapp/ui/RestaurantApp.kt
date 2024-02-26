package com.example.restaurantapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.restaurantapp.common.Helpers
import com.example.restaurantapp.ui.components.TopBar
import com.example.restaurantapp.ui.navigation.Screen
import com.example.restaurantapp.ui.screen.detail.DetailScreen
import com.example.restaurantapp.ui.screen.favorite.FavoriteScreen
import com.example.restaurantapp.ui.screen.home.HomeScreen
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController(),
    viewModel: RestaurantAppViewModel = viewModel(
        factory = Helpers.viewModelFactoryHelper(
            LocalContext.current
        )
    )
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val darkMode by viewModel.darkMode.collectAsState(initial = false)
    val isSearchOpen by viewModel.isSearchOpen.collectAsState(initial = false)
    val searchQuery by viewModel.searchQuery.collectAsState(initial = "")

    viewModel.getDarkMode()

    RestaurantAppTheme(
        darkTheme = darkMode
    ) {
        Scaffold(
            topBar = {
                if (currentRoute != Screen.DetailRestaurant.route) {
                    TopBar(
                        onOpenSearch = {
                            viewModel.setSearchOpen(true)
                        },
                        onGoToHome = {
                            navController.navigate(Screen.Home.route)
                        },
                        onGoToFavorite = {
                            navController.navigate(Screen.Favorite.route)
                        },
                        isInFavoriteScreen = currentRoute == Screen.Favorite.route,
                        isInDarkMode = darkMode,
                        onToggleDarkMode = {
                            viewModel.setDarkMode(!darkMode)
                        },
                        isSearchOpen = isSearchOpen,
                        query = searchQuery,
                        searchPlaceHolder = "Cari restaurant...",
                        onQueryChange = {
                            viewModel.onSearch(it)
                        },
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        onClearQuery = {
                            viewModel.clearQuery()
                        }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(
                    Screen.Home.route
                ) {
                    HomeScreen(
                        navigateToDetail = { id ->
                            navController.navigate(
                                Screen.DetailRestaurant.createRoute(id)
                            )
                        }
                    )
                }
                composable(
                    Screen.Favorite.route
                ) {
                    FavoriteScreen(
                        navigateToDetail = { id ->
                            navController.navigate(
                                Screen.DetailRestaurant.createRoute(id)
                            )
                        }
                    )
                }
                composable(
                    route = Screen.DetailRestaurant.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) {
                    val id = it.arguments?.getString("id")
                    if (id != null) {
                         DetailScreen(
                             restaurantId = id,
                             navigateBack = {
                                 navController.popBackStack()
                             },
                             isInDarkMode = darkMode,
                             onToggleDarkMode = {
                                 viewModel.setDarkMode(!darkMode)
                             }
                         )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantAppTheme {
        RestaurantApp()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    RestaurantAppTheme {
        RestaurantApp()
    }
}