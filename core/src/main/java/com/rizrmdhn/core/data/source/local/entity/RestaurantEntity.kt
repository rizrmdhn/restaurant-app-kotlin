package com.rizrmdhn.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "restaurant")
class RestaurantEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "pictureId")
    val pictureId: String,

    @field:ColumnInfo(name = "city")
    val city: String,

    @field:ColumnInfo(name = "rating")
    val rating: Double,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
): Parcelable

