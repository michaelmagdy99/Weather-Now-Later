package com.example.data.currentweather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "Alert")
data class Alert(

    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString(),
    val fromDate: String, val toDate: String,
    val toTime: String, val fromTime: String,
    val alertType: String,
    val lon: Double,
    val lat: Double,

    )
