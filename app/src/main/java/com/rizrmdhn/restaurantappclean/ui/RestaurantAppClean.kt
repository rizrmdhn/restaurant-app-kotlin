package com.rizrmdhn.restaurantappclean.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rizrmdhn.core.common.Constants
import com.rizrmdhn.restaurantappclean.ui.navigation.Screen
import com.rizrmdhn.restaurantappclean.ui.screen.about.AboutScreen
import com.rizrmdhn.restaurantappclean.ui.screen.detail.DetailScreen
import com.rizrmdhn.restaurantappclean.ui.screen.home.HomeScreen
import com.rizrmdhn.restaurantappclean.ui.theme.RestaurantAppCleanTheme
import com.rizrmdhn.restaurantappclean.utils.DFFavoriteScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController(),
    viewModel: RestaurantAppCleanViewModel = koinViewModel(),
) {
    val darkMode by viewModel.darkMode.collectAsState()

    viewModel.getDarkMode()
    RestaurantAppCleanTheme(
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
                    DFFavoriteScreen(
                        navController = navController,
                        isInDarkMode = darkMode,
                        onToggleDarkMode = {
                            viewModel.setDarkMode(!darkMode)
                        }
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
                    arguments = listOf(navArgument(Constants.id) { type = NavType.StringType })
                ) {
                    val id = it.arguments?.getString(Constants.id) ?: ""
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
    RestaurantAppCleanTheme {
        RestaurantApp()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    RestaurantAppCleanTheme {
        RestaurantApp()
    }
}