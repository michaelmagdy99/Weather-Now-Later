package com.example.domain.currentweather.model

data class CurrentWeatherDomainModel(
    val temperature: Double,
    val condition: String,
    val icon: String,
    val dt: Int?,
    val clouds: Int?,
    val humidity: Int?,
    val windSpeed: Int?,
    val visibility: Int?
)

data class DailyForecastDomainModel(
    val date: Long,
    val temperature: TemperatureDomainModel,
    val weatherCondition: String,
    val icon: String
)

data class TemperatureDomainModel(
    val min: Double,
    val max: Double
)

data class AlertDomainModel(
    val id: String,
    val description: String,
    val event: String,
    val start: Long,
    val end: Long
)

data class WeatherDomainModel(
    val current: CurrentWeatherDomainModel?,
    val dailyForecasts: List<DailyForecastDomainModel>,
    val alerts: List<AlertDomainModel>,
    val lat: Double?, val long: Double?
)
