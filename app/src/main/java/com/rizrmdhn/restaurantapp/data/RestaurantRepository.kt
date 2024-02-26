package com.rizrmdhn.restaurantapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.rizrmdhn.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import com.rizrmdhn.restaurantapp.data.local.room.FavoriteRestaurantDao
import com.rizrmdhn.restaurantapp.data.remote.response.Restaurant
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantDetailResponse
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantListResponse
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantSearchResponse
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantsItem
import com.rizrmdhn.restaurantapp.data.remote.retrofit.ApiService
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
                        val filteredData = responseBody.restaurants.filter { restaurant ->
                            data.none { it.id == restaurant.id }
                        }
                        data.addAll(filteredData)
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

    fun searchRestaurant(query: String): Flow<List<RestaurantsItem>> {
        val client = apiService.searchRestaurants(query)
        client.enqueue(object : Callback<RestaurantSearchResponse> {
            override fun onResponse(
                call: Call<RestaurantSearchResponse>,
                response: Response<RestaurantSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        data.clear()
                        data.addAll(responseBody.restaurants)
                    }
                } else {
                    throw Exception(response.message())
                }
            }

            override fun onFailure(call: Call<RestaurantSearchResponse>, t: Throwable) {
                throw Exception(t.message.toString())
            }
        })

        return flowOf(data)
    }

    fun getFavoriteRestaurant(): Flow<List<FavoriteRestaurantEntity>> {
        return favoriteRestaurantDao.getFavoriteRestaurants()
    }

    fun searchFavoriteRestaurant(query: String): Flow<List<FavoriteRestaurantEntity>> {
        return favoriteRestaurantDao.searchFavoriteRestaurant(query)
    }

    suspend fun insertFavoriteRestaurant(favoriteRestaurantEntity: FavoriteRestaurantEntity) {
        favoriteRestaurantDao.insertFavoriteRestaurant(favoriteRestaurantEntity)
    }

    suspend fun deleteFavoriteRestaurant(id: String) {
        favoriteRestaurantDao.deleteFavoriteRestaurant(id)
    }

    fun isFavoriteRestaurant(id: String): Flow<Boolean> {
        return favoriteRestaurantDao.isFavoriteRestaurant(id)
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