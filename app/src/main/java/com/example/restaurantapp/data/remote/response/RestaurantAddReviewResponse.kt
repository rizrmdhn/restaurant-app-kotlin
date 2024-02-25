package com.example.restaurantapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RestaurantAddReviewResponse(

	@field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)


