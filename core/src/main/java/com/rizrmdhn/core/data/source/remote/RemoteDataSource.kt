package com.rizrmdhn.core.data.source.remote

import android.util.Log
import com.rizrmdhn.core.data.source.remote.network.ApiResponse
import com.rizrmdhn.core.data.source.remote.network.ApiService
import com.rizrmdhn.core.data.source.remote.response.RestaurantDetails
import com.rizrmdhn.core.data.source.remote.response.RestaurantsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(
    private val apiService: ApiService,
) {
    fun getRestaurant(): Flow<ApiResponse<List<RestaurantsItem>>> {
        return flow {
            try {
                val response = apiService.getRestaurants()
                val dataArray = response.restaurants
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.restaurants))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailRestaurant(id: String): Flow<ApiResponse<RestaurantDetails>> {
        return flow {
            try {
                val response = apiService.getRestaurantDetail(id)
                val data = response.restaurant
                Log.d("RemoteDataSource", "getDetailRestaurant: $data")
                emit(ApiResponse.Success(data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchRestaurant(query: String): Flow<ApiResponse<List<RestaurantsItem>>> {
        return flow {
            try {
                val response = apiService.searchRestaurants(query)
                val dataArray = response.restaurants
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.restaurants))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}