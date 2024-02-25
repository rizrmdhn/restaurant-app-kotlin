package com.example.restaurantapp.utils

import android.content.Context
import com.example.restaurantapp.ViewModelFactory
import com.example.restaurantapp.di.Injection

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
}