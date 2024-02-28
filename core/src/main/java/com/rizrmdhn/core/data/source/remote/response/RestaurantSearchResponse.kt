package com.rizrmdhn.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.rizrmdhn.core.data.source.remote.response.RestaurantsItem

data class RestaurantSearchResponse(

    @field:SerializedName("founded")
	val founded: Int,

    @field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem>,

    @field:SerializedName("error")
	val error: Boolean
)
