package com.example.restaurantapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.data.local.dataStore.SettingPreferences
import com.example.restaurantapp.ui.screen.home.HomeScreenViewModel

class ViewModelFactory(
    private val repository: RestaurantRepository,
    private val preferences: SettingPreferences
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantAppViewModel::class.java)) {
            return RestaurantAppViewModel(preferences) as T
        }

        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}