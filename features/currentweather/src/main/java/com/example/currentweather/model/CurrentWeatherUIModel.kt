package com.example.currentweather.model

data class CurrentWeatherUIModel(
    val temperature: String,
    val condition: String,
    val icon: String,
    val dt: Int?,
    val clouds: Int?,
    val humidity: Int?,
    val windSpeed: Int?,
    val visibility: Int?
)

data class DailyForecastUIModel(
    val date: String,
    val temperature: TemperatureUIModel,
    val weatherCondition: String,
    val icon: String
)

data class TemperatureUIModel(
    val min: String,
    val max: String
)

data class AlertUIModel(
    val id: String,
    val description: String,
    val event: String,
    val start: Long,
    val end: Long
)

data class WeatherUIModel(
    val current: CurrentWeatherUIModel?,
    val dailyForecasts: List<DailyForecastUIModel>,
    val alerts: List<AlertUIModel>,
    val long: Double?,
    val lat: Double?,
)
