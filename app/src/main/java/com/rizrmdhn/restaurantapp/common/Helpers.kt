package com.rizrmdhn.restaurantapp.common

import android.content.Context
import com.rizrmdhn.restaurantapp.ui.ViewModelFactory
import com.rizrmdhn.restaurantapp.di.Injection

object Helpers {
    fun viewModelFactoryHelper(context: Context): ViewModelFactory {
        return ViewModelFactory(
            Injection.provideRestaurantRepository(
                context
            ),
            Injection.provideSettingPreferences(
                context
            )
        )
    }

    fun smallRestaurantImage(pictureId: String): String {
        return "https://restaurant-api.dicoding.dev/images/small/$pictureId"
    }

    fun mediumRestaurantImage(pictureId: String): String {
        return "https://restaurant-api.dicoding.dev/images/medium/$pictureId"
    }

    fun largeRestaurantImage(pictureId: String): String {
        return "https://restaurant-api.dicoding.dev/images/large/$pictureId"
    }

    fun avatarGenerator(username: String): String {
        return "https://ui-avatars.com/api/?name=$username&length=1&background=random&size=128"
    }
}