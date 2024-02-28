package com.rizrmdhn.core.utils

import com.rizrmdhn.core.data.source.local.entity.RestaurantEntity
import com.rizrmdhn.core.data.source.remote.response.RestaurantsItem
import com.rizrmdhn.core.domain.model.Restaurant

object DataMapper {
    fun mapResponsesToEntities(input: List<RestaurantsItem>): List<RestaurantEntity> {
        val restaurantList = ArrayList<RestaurantEntity>()
        input.map {
            val restaurant = RestaurantEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                pictureId = it.pictureId,
                city = it.city,
                rating = it.rating as Double
            )
            restaurantList.add(restaurant)
        }
        return restaurantList
    }

    fun mapEntitiesToDomain(input: List<RestaurantEntity>): List<Restaurant> =
        input.map {
            Restaurant(
                id = it.id,
                name = it.name,
                description = it.description,
                pictureId = it.pictureId,
                city = it.city,
                rating = it.rating
            )
        }

    fun mapDomainToEntity(input: Restaurant) = RestaurantEntity(
        id = input.id,
        name = input.name,
        description = input.description,
        pictureId = input.pictureId,
        city = input.city,
        rating = input.rating
    )
}