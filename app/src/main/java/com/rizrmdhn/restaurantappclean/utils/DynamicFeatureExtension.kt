package com.rizrmdhn.restaurantappclean.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun DFFavoriteScreen(
    navController: NavHostController,
    isInDarkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {
    DynamicFeatureUtils.DfFavoriteScreen(
        navController = navController,
        isInDarkMode = isInDarkMode,
        onToggleDarkMode = onToggleDarkMode
    )
}
