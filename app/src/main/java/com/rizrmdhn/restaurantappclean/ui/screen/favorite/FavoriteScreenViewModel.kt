package com.rizrmdhn.restaurantappclean.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rizrmdhn.core.domain.usecase.RestaurantUseCase

class FavoriteScreenViewModel(
    restaurantUseCase: RestaurantUseCase
) : ViewModel() {
    val favoriteRestaurant = restaurantUseCase.getFavoriteRestaurant()
}