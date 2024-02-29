package com.rizrmdhn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RestaurantDetailResponse(

	@field:SerializedName("restaurant")
	val restaurant: RestaurantDetails,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DrinksItem(

	@field:SerializedName("name")
	val name: String
)

data class FoodsItem(

	@field:SerializedName("name")
	val name: String
)

data class RestaurantDetails(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("pictureId")
	val pictureId: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem>,

	@field:SerializedName("menus")
	val menus: Menus
)

data class Menus(

	@field:SerializedName("foods")
	val foods: List<FoodsItem>,

	@field:SerializedName("drinks")
	val drinks: List<DrinksItem>
)

data class CustomerReviewsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("review")
	val review: String,

	@field:SerializedName("name")
	val name: String
)

data class CategoriesItem(

	@field:SerializedName("name")
	val name: String
)
