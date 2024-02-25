package com.example.restaurantapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.restaurantapp.data.local.room.FavoriteRestaurantDao
import com.example.restaurantapp.data.remote.response.Restaurant
import com.example.restaurantapp.data.remote.response.RestaurantDetailResponse
import com.example.restaurantapp.data.remote.response.RestaurantListResponse
import com.example.restaurantapp.data.remote.response.RestaurantsItem
import com.example.restaurantapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantRepository(
    private val apiService: ApiService,
    private val favoriteRestaurantDao: FavoriteRestaurantDao
) {
    private val data = mutableStateListOf<RestaurantsItem>()
    private var detailRestaurant by mutableStateOf<Restaurant?>(null)

    private var query by mutableStateOf("")

    fun getRestaurant(): Flow<List<RestaurantsItem>> {
        val client = apiService.getRestaurants()
        client.enqueue(object : Callback<RestaurantListResponse> {
            override fun onResponse(
                call: Call<RestaurantListResponse>,
                response: Response<RestaurantListResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        data.addAll(responseBody.restaurants)
                    }
                } else {
                    throw Exception(response.message())
                }
            }

            override fun onFailure(call: Call<RestaurantListResponse>, t: Throwable) {
                throw Exception(t.message.toString())
            }
        })

        return flowOf(data)
    }

    fun getDetailRestaurant(id: String): Flow<Restaurant?> {
        val client = apiService.getRestaurantDetail(id)
        client.enqueue(object : Callback<RestaurantDetailResponse> {
            override fun onResponse(
                call: Call<RestaurantDetailResponse>,
                response: Response<RestaurantDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        detailRestaurant = responseBody.restaurant
                    } else {
                        throw Exception("Restaurant not found")
                    }
                } else {
                    throw Exception(response.message())
                }
            }

            override fun onFailure(call: Call<RestaurantDetailResponse>, t: Throwable) {
                throw Exception(t.message.toString())
            }
        })

        return flowOf(detailRestaurant)
    }


    companion object {
        @Volatile
        private var instance: RestaurantRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteRestaurantDao: FavoriteRestaurantDao
        ): RestaurantRepository = instance ?: synchronized(this) {
            instance ?: RestaurantRepository(apiService, favoriteRestaurantDao)
        }.also { instance = it }
    }
}