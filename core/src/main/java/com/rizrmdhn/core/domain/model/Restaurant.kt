package com.rizrmdhn.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val city: String,
    val pictureId: String,
    val rating: Double
): Parcelable