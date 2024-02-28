package com.rizrmdhn.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantDetail(
    val id: String,
    val name: String,
    val description: String,
    val city: String,
    val address: String,
    val pictureId: String,
    val categories: List<Category>,
    val menus: Menu,
    val rating: Double,
    val customerReviews: List<Review>
): Parcelable