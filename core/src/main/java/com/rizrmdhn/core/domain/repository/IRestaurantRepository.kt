package com.rizrmdhn.core.domain.repository

import com.rizrmdhn.core.data.Resource
import com.rizrmdhn.core.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepository {
    fun getRestaurant(): Flow<Resource<List<Restaurant>>>
}