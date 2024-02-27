package com.rizrmdhn.restaurantapp.ui

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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rizrmdhn.restaurantapp.common.Helpers
import com.rizrmdhn.restaurantapp.ui.navigation.Screen
import com.rizrmdhn.restaurantapp.ui.screen.about.AboutScreen
import com.rizrmdhn.restaurantapp.ui.screen.detail.DetailScreen
import com.rizrmdhn.restaurantapp.ui.screen.favorite.FavoriteScreen
import com.rizrmdhn.restaurantapp.ui.screen.home.HomeScreen
import com.rizrmdhn.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController(),
    viewModel: RestaurantAppViewModel = viewModel(
        factory = Helpers.viewModelFactoryHelper(
            LocalContext.current
        )
    )
) {
    val darkMode by viewModel.darkMode.collectAsState(initial = false)

    viewModel.getDarkMode()

    RestaurantAppTheme(
        darkTheme = darkMode
    ) {
        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(
                    Screen.Home.route
                ) {
                    HomeScreen(
                        navController = navController,
                        onToggleDarkMode = {
                            viewModel.setDarkMode(!darkMode)
                        },
                        isInDarkMode = darkMode,
                    )
                }
                composable(
                    Screen.Favorite.route
                ) {
                    FavoriteScreen(
                        navController = navController,
                        onToggleDarkMode = {
                            viewModel.setDarkMode(!darkMode)
                        },
                        isInDarkMode = darkMode,
                    )
                }
                composable(Screen.About.route) {
                    AboutScreen(
                        navigateBack = {
                            navController.navigateUp()
                        },
                        isInDarkMode = darkMode,
                        onToggleDarkMode = {
                            viewModel.setDarkMode(!darkMode)
                        }
                    )
                }
                composable(
                    route = Screen.DetailRestaurant.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) {
                    val id = it.arguments?.getString("id") ?: ""
                    DetailScreen(restaurantId = id, navigateBack = {
                        navController.navigateUp()
                    }, isInDarkMode = darkMode, onToggleDarkMode = {
                        viewModel.setDarkMode(!darkMode)
                    })
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