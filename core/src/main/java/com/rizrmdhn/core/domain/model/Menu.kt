package com.rizrmdhn.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Menu(
    val foods: List<Food>,
    val drinks: List<Drink>
): Parcelable