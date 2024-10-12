package com.example.data.currentweather.repository

import com.example.data.currentweather.datasource.ICurrentWeatherRemoteDataSource
import com.example.data.currentweather.mapper.toDomain
import com.example.data.currentweather.model.WeatherResponse
import com.example.domain.currentweather.model.WeatherDomainModel
import com.example.domain.currentweather.repository.ICurrentWeatherRepo
import javax.inject.Inject

class CurrentWeatherRepo @Inject constructor(
    private val currentWeatherRemoteDataSource: ICurrentWeatherRemoteDataSource
) : ICurrentWeatherRepo {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        appId: String,
        lang: String,
        units: String
    ): WeatherDomainModel {
        val weatherResponse = currentWeatherRemoteDataSource.getCurrentWeather(lat, lon, appId, lang, units)
        return weatherResponse.toDomain()
    }
}