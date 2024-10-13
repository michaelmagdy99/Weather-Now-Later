package com.example.currentweather.model

import com.example.domain.currentweather.model.AlertDomainModel
import com.example.domain.currentweather.model.CurrentWeatherDomainModel
import com.example.domain.currentweather.model.DailyForecastDomainModel
import com.example.domain.currentweather.model.WeatherDomainModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun WeatherDomainModel.toUI(): WeatherUIModel {
    return WeatherUIModel(
        current = current?.toUI(),
        dailyForecasts = dailyForecasts.map { it.toUI() },
        alerts = alerts.map { it.toUI() },
        lat = lat,
        long = long,
    )
}

fun CurrentWeatherDomainModel.toUI(): CurrentWeatherUIModel {
    return CurrentWeatherUIModel(
        temperature = "${temperature.toInt()} °C",
        condition = condition,
        icon = icon,
        dt = dt,
        clouds = clouds,
        humidity = humidity,
        windSpeed = windSpeed,
        visibility =visibility
    )
}

fun DailyForecastDomainModel.toUI(): DailyForecastUIModel {
    return DailyForecastUIModel(
        date = convertTimestampToDateString(date),
        temperature = TemperatureUIModel(
            min = "${temperature.min.toInt()} °C",
            max = "${temperature.max.toInt()} °C"
        ),
        weatherCondition = weatherCondition,
        icon = icon
    )
}

fun AlertDomainModel.toUI(): AlertUIModel {
    return AlertUIModel(
        id = id,
        description = description,
        event = event,
        start = start,
        end = end
    )
}

fun convertTimestampToDateString(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return format.format(date)
}