package com.rizrmdhn.core.domain.usecase

import com.rizrmdhn.core.data.Resource
import com.rizrmdhn.core.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantUseCase {
    fun getRestaurant(): Flow<Resource<List<Restaurant>>>
}