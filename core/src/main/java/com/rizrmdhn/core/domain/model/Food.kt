package com.rizrmdhn.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val name: String,
) : Parcelable