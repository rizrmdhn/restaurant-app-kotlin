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

    @Query("SELECT EXISTS(SELECT 1 FROM restaurant WHERE id=:id AND isFavorite = 1)")
    fun isFavoriteRestaurant(id: String): Flow<Boolean>

    @Query("SELECT * FROM restaurant WHERE name LIKE '%' || :query || '%'")
    fun searchRestaurant(query: String): Flow<List<RestaurantEntity>>

    @Query("SELECT * FROM restaurant WHERE name LIKE '%' || :query || '%' AND isFavorite = 1")
    fun searchFavoriteRestaurant(query: String): Flow<List<RestaurantEntity>>
}