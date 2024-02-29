package com.rizrmdhn.core.domain.repository

import com.rizrmdhn.core.data.Resource
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.model.RestaurantDetail
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepository {
    fun getRestaurant(): Flow<Resource<List<Restaurant>>>

    fun getDetailRestaurant(id: String): Flow<Resource<RestaurantDetail>>

    fun getFavoriteRestaurant(): Flow<Resource<List<Restaurant>>>

    fun setFavoriteRestaurant(restaurant: Restaurant, state: Boolean)

    fun isFavoriteRestaurant(id: String): Flow<Boolean>

    fun searchRestaurant(query: String):Flow<Resource<List<Restaurant>>>

    fun searchFavoriteRestaurant(query: String): Flow<Resource<List<Restaurant>>>

    fun getDarkMode(): Flow<Boolean>

    suspend fun setDarkMode(isDarkMode: Boolean)
}