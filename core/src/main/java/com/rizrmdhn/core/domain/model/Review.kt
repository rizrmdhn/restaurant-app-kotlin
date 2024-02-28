package com.rizrmdhn.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val name: String,
    val review: String,
    val date: String
): Parcelable