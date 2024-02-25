package com.example.restaurantapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.data.local.dataStore.SettingPreferences

class ViewModelFactory(
    private val repository: RestaurantRepository,
    private val preferences: SettingPreferences
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}