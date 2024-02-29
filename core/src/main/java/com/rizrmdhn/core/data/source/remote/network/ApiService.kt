package com.rizrmdhn.core.data.source.remote.network

import com.rizrmdhn.core.data.source.remote.response.GithubDetailUser
import com.rizrmdhn.core.data.source.remote.response.RestaurantDetailResponse
import com.rizrmdhn.core.data.source.remote.response.RestaurantListResponse
import com.rizrmdhn.core.data.source.remote.response.RestaurantSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    suspend fun getRestaurants(): RestaurantListResponse

    @GET("detail/{id}")
    suspend fun getRestaurantDetail(
        @Path("id") id: String
    ): RestaurantDetailResponse

    @GET("search")
    suspend fun searchRestaurants(
        @Query("q") query: String
    ): RestaurantSearchResponse

}

