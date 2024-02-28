package com.rizrmdhn.restaurantappclean.ui

import com.rizrmdhn.restaurantappclean.ui.theme.RestaurantAppCleanTheme
import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rizrmdhn.restaurantapp.ui.navigation.Screen
import com.rizrmdhn.restaurantappclean.ui.screen.home.HomeScreen

@Composable
fun RestaurantApp(
    navController: NavHostController = rememberNavController(),
) {
    RestaurantAppCleanTheme(
        darkTheme = false
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

                        },
                        isInDarkMode = false,
                    )
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