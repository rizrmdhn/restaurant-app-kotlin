package com.rizrmdhn.core.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name: String
) : android.os.Parcelable