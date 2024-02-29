package com.rizrmdhn.core.domain.usecase

import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.repository.IRestaurantRepository

class RestaurantInteractor(
    private val restaurantRepository: IRestaurantRepository
): RestaurantUseCase {
    override fun getRestaurant() = restaurantRepository.getRestaurant()

    override fun getDetailRestaurant(id: String) = restaurantRepository.getDetailRestaurant(id)

    override fun getFavoriteRestaurant() = restaurantRepository.getFavoriteRestaurant()

    override fun setFavoriteRestaurant(restaurant: Restaurant, state: Boolean) = restaurantRepository.setFavoriteRestaurant(restaurant, state)

    override fun isFavoriteRestaurant(id: String) = restaurantRepository.isFavoriteRestaurant(id)

    override fun searchRestaurant(query: String) = restaurantRepository.searchRestaurant(query)

    override fun searchFavoriteRestaurant(query: String) = restaurantRepository.searchFavoriteRestaurant(query)

    override fun getDarkMode() = restaurantRepository.getDarkMode()

    override suspend fun setDarkMode(isDarkMode: Boolean) = restaurantRepository.setDarkMode(isDarkMode)
}