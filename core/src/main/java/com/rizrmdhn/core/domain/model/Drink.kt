package com.rizrmdhn.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    val name: String,
) : Parcelable