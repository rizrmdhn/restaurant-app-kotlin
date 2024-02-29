package com.rizrmdhn.core.utils

import com.rizrmdhn.core.data.source.local.entity.RestaurantEntity
import com.rizrmdhn.core.data.source.remote.response.RestaurantDetails
import com.rizrmdhn.core.data.source.remote.response.RestaurantsItem
import com.rizrmdhn.core.domain.model.Category
import com.rizrmdhn.core.domain.model.Drink
import com.rizrmdhn.core.domain.model.Food
import com.rizrmdhn.core.domain.model.Menu
import com.rizrmdhn.core.domain.model.Restaurant
import com.rizrmdhn.core.domain.model.RestaurantDetail
import com.rizrmdhn.core.domain.model.Review

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

    fun mapRestaurantDetailToRestaurant(input: RestaurantDetail) = Restaurant(
        id = input.id,
        name = input.name,
        description = input.description,
        pictureId = input.pictureId,
        city = input.city,
        rating = input.rating
    )

    fun mapDetailResponseToDomain(input: RestaurantDetails) = RestaurantDetail(
        id = input.id,
        name = input.name,
        description = input.description,
        pictureId = input.pictureId,
        city = input.city,
        rating = input.rating as Double,
        address = input.address,
        categories = input.categories.map {
            Category(
                name = it.name
            )
        },
        menus = Menu(
            foods = input.menus.foods.map {
                Food(
                    name = it.name
                )
            },
            drinks = input.menus.drinks.map {
                Drink(
                    name = it.name
                )
            }
        ),
        customerReviews = input.customerReviews.map {
            Review(
                name = it.name,
                review = it.review,
                date = it.date
            )
        }
    )
}