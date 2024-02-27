package com.rizrmdhn.restaurantapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rizrmdhn.restaurantapp.data.GithubRepository
import com.rizrmdhn.restaurantapp.data.RestaurantRepository
import com.rizrmdhn.restaurantapp.data.local.dataStore.SettingPreferences
import com.rizrmdhn.restaurantapp.ui.screen.about.AboutScreenViewModel
import com.rizrmdhn.restaurantapp.ui.screen.detail.DetailScreenViewModel
import com.rizrmdhn.restaurantapp.ui.screen.favorite.FavoriteScreenViewModel
import com.rizrmdhn.restaurantapp.ui.screen.home.HomeScreenViewModel

class ViewModelFactory(
    private val repository: RestaurantRepository,
    private val githubRepository: GithubRepository,
    private val preferences: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantAppViewModel::class.java)) {
            return RestaurantAppViewModel(preferences) as T
        }

        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(FavoriteScreenViewModel::class.java)) {
            return FavoriteScreenViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(AboutScreenViewModel::class.java)) {
            return AboutScreenViewModel(githubRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}