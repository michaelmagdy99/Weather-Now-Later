package com.example.data.currentweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.Serializable

@Entity(tableName = "fav_table")
data class FaviourateLocationDto(
     @PrimaryKey val locationKey: LocationKey,
     val countryName: String,
     val temp: String
):Serializable

data class LocationKey(
     val lat: Double,
     val long: Double
)
