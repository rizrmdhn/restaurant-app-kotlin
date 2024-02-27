package com.rizrmdhn.restaurantapp.data.remote.retrofit

import com.rizrmdhn.restaurantapp.data.remote.response.GithubDetailUser
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantAddReviewResponse
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantDetailResponse
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantListResponse
import com.rizrmdhn.restaurantapp.data.remote.response.RestaurantSearchResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    fun getRestaurants(): Call<RestaurantListResponse>

    @GET("detail/{id}")
    fun getRestaurantDetail(
        @Path("id") id: String
    ): Call<RestaurantDetailResponse>

    @GET("search")
    fun searchRestaurants(
        @Query("q") query: String
    ): Call<RestaurantSearchResponse>

}

interface ApiGithubService {
    @GET("users/{username}")
    fun getDetailGithubUser(
        @Path("username") username: String
    ): Call<GithubDetailUser>
}