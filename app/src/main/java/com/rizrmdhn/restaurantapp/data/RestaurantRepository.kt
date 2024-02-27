package com.rizrmdhn.restaurantapp.data

import com.rizrmdhn.restaurantapp.data.Result
import com.rizrmdhn.restaurantapp.data.local.entity.FavoriteRestaurantEntity
import com.rizrmdhn.restaurantapp.data.local.room.FavoriteRestaurantDao
import com.rizrmdhn.restaurantapp.data.remote.response.Restaurant
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantsItem
import com.rizrmdhn.restaurantapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class RestaurantRepository(
    private val apiService: ApiService,
    private val favoriteRestaurantDao: FavoriteRestaurantDao
) {

    fun getRestaurant(): Flow<List<RestaurantsItem>> {
        return flow {
            try {
                val response = apiService.getRestaurants()
                response.awaitResponse().let {
                    if (it.isSuccessful) {
                        val responseBody = it.body()
                        if (responseBody != null) {
                            emit(responseBody.restaurants)
                        } else {
                            throw Exception("Restaurant not found")
                        }
                    } else {
                        throw Exception(it.message())
                    }
                }
            } catch (e: Exception) {
                throw Exception(e.message.toString())
            }
        }
    }


    fun getDetailRestaurant(id: String): Flow<Restaurant?> {
        return flow {
            try {
                val response = apiService.getRestaurantDetail(id)
                response.awaitResponse().let {
                    if (it.isSuccessful) {
                        val responseBody = it.body()
                        if (responseBody != null) {
                            emit(responseBody.restaurant)
                        } else {
                            throw Exception("Restaurant not found")
                        }
                    } else {
                        throw Exception(it.message())
                    }
                }
            } catch (e: Exception) {
                throw Exception(e.message.toString())
            }
        }
    }

    fun searchRestaurant(query: String): Flow<List<RestaurantsItem>> {
        return flow {
            try {
                val response = apiService.searchRestaurants(query)
                response.awaitResponse().let {
                    if (it.isSuccessful) {
                        val responseBody = it.body()
                        if (responseBody != null) {
                            emit(responseBody.restaurants)
                        } else {
                            throw Exception("Restaurant not found")
                        }
                    } else {
                        throw Exception(it.message())
                    }
                }
            } catch (e: Exception) {
                throw Exception(e.message.toString())
            }
        }
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