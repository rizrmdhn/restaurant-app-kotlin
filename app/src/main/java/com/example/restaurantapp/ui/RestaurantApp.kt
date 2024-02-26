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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurantapp.common.Helpers
import com.example.restaurantapp.ui.components.TopBar
import com.example.restaurantapp.ui.navigation.Screen
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
    val darkMode by viewModel.darkMode.collectAsState(initial = false)

    viewModel.getDarkMode()

    RestaurantAppTheme(
        darkTheme = darkMode
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onGoToSearch = {},
                    onGoToFavorite = {},
                    isInDarkMode = darkMode,
                    onToggleDarkMode = {
                        viewModel.setDarkMode(!darkMode)
                    },
                )
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
                    HomeScreen()
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