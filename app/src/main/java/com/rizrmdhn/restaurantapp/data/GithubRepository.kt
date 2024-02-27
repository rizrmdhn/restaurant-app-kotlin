package com.rizrmdhn.restaurantapp.data

import com.rizrmdhn.restaurantapp.common.Constants
import com.rizrmdhn.restaurantapp.data.remote.response.GithubDetailUser
import com.rizrmdhn.restaurantapp.data.remote.retrofit.ApiGithubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class GithubRepository(
    private val apiGithubService: ApiGithubService
) {

    fun getDetailGithubUser(): Flow<GithubDetailUser> {
        return flow {
            try {
                val response = apiGithubService.getDetailGithubUser(Constants.githubUsername)
                response.awaitResponse().let {
                    if (it.isSuccessful) {
                        val responseBody = it.body()
                        if (responseBody != null) {
                            emit(responseBody)
                        } else {
                            throw Exception("Restaurant not found")
                        }
                    }
                }
            } catch (e: Exception) {
                throw Exception(e.message.toString())
            }
        }
    }
}