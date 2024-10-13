package com.example.data.currentweather.mapper

import com.example.data.currentweather.model.AlertsItem
import com.example.data.currentweather.model.Current
import com.example.data.currentweather.model.DailyItem
import com.example.data.currentweather.model.WeatherResponse
import com.example.domain.currentweather.model.AlertDomainModel
import com.example.domain.currentweather.model.CurrentWeatherDomainModel
import com.example.domain.currentweather.model.DailyForecastDomainModel
import com.example.domain.currentweather.model.TemperatureDomainModel
import com.example.domain.currentweather.model.WeatherDomainModel

fun WeatherResponse.toDomain(): WeatherDomainModel {
    return WeatherDomainModel(
        current = current?.toDomain(),
        dailyForecasts = daily?.map { it!!.toDomain() } ?: emptyList(),
        alerts = alerts?.map { it!!.toDomain() } ?: emptyList(),
        lat = lat,
        long = lon,
    )
}

fun Current.toDomain(): CurrentWeatherDomainModel {
    return CurrentWeatherDomainModel(
        temperature = temp ?: 0.0,
        condition = weather?.firstOrNull()?.description ?: "Unknown",
        icon = weather?.firstOrNull()?.icon ?: "",
        dt = dt,
        clouds = clouds,
        humidity = humidity,
        windSpeed = windSpeed,
        visibility =visibility

    )
}

fun DailyItem.toDomain(): DailyForecastDomainModel {
    return DailyForecastDomainModel(
        date = dt ?: 0L,
        temperature = TemperatureDomainModel(
            min = temp?.min as? Double ?: 0.0,
            max = temp?.max as? Double ?: 0.0
        ),
        weatherCondition = weather?.firstOrNull()?.description ?: "Unknown",
        icon = weather?.firstOrNull()?.icon ?: ""
    )
}

fun AlertsItem.toDomain(): AlertDomainModel {
    return AlertDomainModel(
        id = start.toString(),
        description = description ?: "",
        event = event ?: "",
        start = start?.toLong() ?: 0L,
        end = end?.toLong() ?: 0L
    )
}
