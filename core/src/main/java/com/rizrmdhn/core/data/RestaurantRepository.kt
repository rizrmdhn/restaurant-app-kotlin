package com.rizrmdhn.core.data

import com.rizrmdhn.core.data.source.local.LocalDataSource
import com.rizrmdhn.core.data.source.remote.RemoteDataSource
import com.rizrmdhn.core.data.source.remote.network.ApiResponse
import com.rizrmdhn.core.data.source.remote.response.RestaurantsItem
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.model.RestaurantDetail
import com.rizrmdhn.core.domain.repository.IRestaurantRepository
import com.rizrmdhn.core.utils.AppExecutors
import com.rizrmdhn.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RestaurantRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IRestaurantRepository {

    override fun getRestaurant(): Flow<Resource<List<Restaurant>>> =
        object : NetworkBoundResource<List<Restaurant>, List<RestaurantsItem>>() {
            override fun shouldFetch(data: List<Restaurant>?): Boolean = data.isNullOrEmpty()

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

    override fun getDetailRestaurant(id: String): Flow<Resource<RestaurantDetail>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getDetailRestaurant(id).collect { apiResponse ->
                when (apiResponse) {
                    is ApiResponse.Success -> {
                        val restaurantDetail =
                            DataMapper.mapDetailResponseToDomain(apiResponse.data)
                        emit(Resource.Success(restaurantDetail))
                    }

                    is ApiResponse.Empty -> {
                        emit(Resource.Error<RestaurantDetail>("Empty Data"))
                    }

                    is ApiResponse.Error -> {
                        emit(Resource.Error<RestaurantDetail>(apiResponse.errorMessage))
                    }
                }
            }
        }
    }

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


    override fun setFavoriteRestaurant(restaurant: Restaurant, state: Boolean) {
        val restaurantEntity = DataMapper.mapDomainToEntity(restaurant)
        appExecutors.diskIO().execute { localDataSource.setFavoriteRestaurant(restaurantEntity, state) }
    }

    override fun searchRestaurant(query: String): Flow<Resource<List<Restaurant>>> =
        object : NetworkBoundResource<List<Restaurant>, List<RestaurantsItem>>() {
            override fun shouldFetch(data: List<Restaurant>?): Boolean = data.isNullOrEmpty()

            override fun loadFromDB(): Flow<List<Restaurant>> {
                return localDataSource.searchRestaurant(query).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<RestaurantsItem>>> {
                return remoteDataSource.searchRestaurant(query)
            }

            override suspend fun saveCallResult(data: List<RestaurantsItem>) {
                val restaurantList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertRestaurant(restaurantList)
            }
        }.asFlow()

    override fun searchFavoriteRestaurant(query: String): Flow<Resource<List<Restaurant>>> {
        return localDataSource.searchFavoriteRestaurant(query).map {
            Resource.Success(DataMapper.mapEntitiesToDomain(it))
        }
    }

    override fun isFavoriteRestaurant(id: String): Flow<Boolean> {
        return localDataSource.isFavoriteRestaurant(id)
    }

    override fun getDarkMode(): Flow<Boolean> {
        return localDataSource.getThemeSetting()
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        localDataSource.saveThemeSetting(isDarkMode)
    }
}