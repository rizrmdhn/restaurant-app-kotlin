package com.rizrmdhn.core.domain.usecase

import com.rizrmdhn.core.domain.repository.IRestaurantRepository

class RestaurantInteractor(
    private val restaurantRepository: IRestaurantRepository
): RestaurantUseCase {
    override fun getRestaurant() = restaurantRepository.getRestaurant()

}