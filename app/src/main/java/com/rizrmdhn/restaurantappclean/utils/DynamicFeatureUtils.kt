package com.rizrmdhn.restaurantappclean.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.rizrmdhn.core.ui.favorite.FavoriteScreenViewModel
import com.rizrmdhn.restaurantappclean.ui.components.ErrorScreen
import org.koin.androidx.compose.koinViewModel


object DynamicFeatureUtils {
    @Composable
    fun DfFavoriteScreen(
        navController: NavHostController,
        isInDarkMode: Boolean,
        onToggleDarkMode: () -> Unit
    ) {

        return LoadDF(
            className = DynamicFeaturePackageFactory.DFFavorite.DF_FAVORITE_SCREEN,
            methodName = DynamicFeaturePackageFactory.DFFavorite.COMPOSE_METHOD_NAME,
            viewModel = koinViewModel<FavoriteScreenViewModel>(),
            navController = navController,
            isInDarkMode = isInDarkMode,
            onToggleDarkMode = onToggleDarkMode,
            errorText = "Dynamic Feature Favorite not found"
        )
    }

    @Composable
    private fun LoadDF(
        className: String,
        methodName: String,
        objectInstance: Any = Any(),
        viewModel: ViewModel,
        navController: NavHostController,
        isInDarkMode: Boolean,
        onToggleDarkMode: () -> Unit,
        errorText: String = "Dynamic Feature not found"
    ) {
        val dfClass = loadClassByReflection(className)
        if (dfClass != null) {
            val composer = currentComposer
            val method = findMethodByReflection(
                dfClass,
                methodName
            )
            Log.d("DynamicFeatureUtils", "viewModel: $viewModel")
            if (method != null) {
                val isMethodInvoked =
                    invokeMethod(
                        method,
                        objectInstance,
                        viewModel,
                        navController,
                        isInDarkMode,
                        onToggleDarkMode,
                        composer,
                        0,
                        0
                    )
                if (!isMethodInvoked) {
                    return ErrorScreen(error = "Method not invoked", navController = navController)
                }
            } else {
                return ErrorScreen(error = "Method not found", navController = navController)
            }
        } else {
            return ErrorScreen(error = errorText, navController = navController)
        }
    }

}

