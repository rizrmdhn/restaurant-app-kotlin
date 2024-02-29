package com.rizrmdhn.restaurantappclean.utils

object DynamicFeaturePackageFactory {
    private const val PACKAGE = "com.rizrmdhn."

    object DFFavorite {
        const val DF_FAVORITE_SCREEN = PACKAGE.plus("favorite.ui.favorite.FavoriteScreenKt")
        const val COMPOSE_METHOD_NAME = "FavoriteScreen"
    }
}