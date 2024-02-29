package com.rizrmdhn.core.data.source.local

import com.rizrmdhn.core.data.source.local.entity.RestaurantEntity
import com.rizrmdhn.core.data.source.local.preferences.SettingPreferences
import com.rizrmdhn.core.data.source.local.room.RestaurantDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val restaurantDao: RestaurantDao,
    private val settingPreferences: SettingPreferences
) {
    fun getAllRestaurant(): Flow<List<RestaurantEntity>> = restaurantDao.getAllRestaurants()

    fun getFavoriteRestaurant(): Flow<List<RestaurantEntity>> = restaurantDao.getFavoriteRestaurants()

    suspend fun insertRestaurant(restaurantList: List<RestaurantEntity>) = restaurantDao.insertRestaurants(restaurantList)

    fun setFavoriteRestaurant(restaurant: RestaurantEntity, newState: Boolean) {
        restaurant.isFavorite = newState
        restaurantDao.updateFavoriteRestaurant(restaurant)
    }

    fun getThemeSetting(): Flow<Boolean> = settingPreferences.getThemeSetting()

    suspend fun saveThemeSetting(isDarkMode: Boolean) = settingPreferences.saveThemeSetting(isDarkMode)
}