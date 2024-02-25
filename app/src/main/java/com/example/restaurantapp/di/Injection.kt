package com.example.restaurantapp.di

import android.content.Context
import com.example.restaurantapp.data.RestaurantRepository
import com.example.restaurantapp.data.local.dataStore.SettingPreferences
import com.example.restaurantapp.data.local.room.FavoriteRestaurantDatabase
import com.example.restaurantapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRestaurantRepository(
        context: Context
    ): RestaurantRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteRestaurantDatabase.getInstance(context)
        val dao = database.favoriteRestaurantDao()

        return RestaurantRepository.getInstance(apiService, dao)
    }

    fun provideSettingPreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context)
    }
}

