package com.rizrmdhn.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rizrmdhn.core.data.source.local.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurant")
    fun getAllRestaurants(): Flow<List<RestaurantEntity>>

    @Query("SELECT * FROM restaurant where isFavorite = 1")
    fun getFavoriteRestaurants(): Flow<List<RestaurantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<RestaurantEntity>)

    @Update
    fun updateFavoriteRestaurant(restaurant: RestaurantEntity)
}