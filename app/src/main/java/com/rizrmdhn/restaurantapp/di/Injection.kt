package com.rizrmdhn.restaurantapp.di

import android.content.Context
import com.rizrmdhn.restaurantapp.data.GithubRepository
import com.rizrmdhn.restaurantapp.data.RestaurantRepository
import com.rizrmdhn.restaurantapp.data.local.dataStore.SettingPreferences
import com.rizrmdhn.restaurantapp.data.local.room.FavoriteRestaurantDatabase
import com.rizrmdhn.restaurantapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRestaurantRepository(
        context: Context
    ): RestaurantRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteRestaurantDatabase.getInstance(context)
        val dao = database.favoriteRestaurantDao()

        return RestaurantRepository.getInstance(apiService, dao)
    }

    fun provideGithubRepository(
        context: Context
    ): GithubRepository {
        val apiGithubService = ApiConfig.getApiGithubService()

        return GithubRepository(apiGithubService)
    }

    fun provideSettingPreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context)
    }
}

