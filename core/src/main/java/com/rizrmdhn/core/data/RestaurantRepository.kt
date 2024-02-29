package com.rizrmdhn.core.data

import com.rizrmdhn.core.data.source.local.LocalDataSource
import com.rizrmdhn.core.data.source.remote.RemoteDataSource
import com.rizrmdhn.core.data.source.remote.network.ApiResponse
import com.rizrmdhn.core.data.source.remote.response.RestaurantsItem
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.repository.IRestaurantRepository
import com.rizrmdhn.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RestaurantRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IRestaurantRepository {

    override fun getRestaurant(): Flow<Resource<List<Restaurant>>> =
        object : NetworkBoundResource<List<Restaurant>, List<RestaurantsItem>>() {
            override fun shouldFetch(data: List<Restaurant>?): Boolean = true

            override fun loadFromDB(): Flow<List<Restaurant>> {
                return localDataSource.getAllRestaurant().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<RestaurantsItem>>> {
                return remoteDataSource.getRestaurant()
            }

            override suspend fun saveCallResult(data: List<RestaurantsItem>) {
                val restaurantList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertRestaurant(restaurantList)
            }
        }.asFlow()

    override fun getFavoriteRestaurant(): Flow<Resource<List<Restaurant>>> {
        return flow {
            emit(Resource.Loading())
            localDataSource.getFavoriteRestaurant().map {
                DataMapper.mapEntitiesToDomain(it)
            }.collect {
                emit(Resource.Success(it))
            }
        }
    }


    override fun getDarkMode(): Flow<Boolean> {
        return localDataSource.getThemeSetting()
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        localDataSource.saveThemeSetting(isDarkMode)
    }
}