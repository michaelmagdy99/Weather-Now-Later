package com.example.data.currentweather.datasource

import com.example.data.currentweather.model.WeatherResponse
import com.example.data.currentweather.webservice.CurrentWeatherWebService
import javax.inject.Inject

class CurrentWeatherRemoteDataSource @Inject constructor(
    private val currentWeatherRemoteDataSource: CurrentWeatherWebService
): ICurrentWeatherRemoteDataSource {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        appId: String,
        lang: String,
        units: String
    ): WeatherResponse {
       return currentWeatherRemoteDataSource.getCurrentWeather(lat, lon, appId, lang, units)
    }

}