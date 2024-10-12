package com.example.data.currentweather.datasource

import com.example.data.currentweather.model.WeatherResponse

interface ICurrentWeatherRemoteDataSource {
    suspend fun getCurrentWeather(lat: Double, lon: Double, appId:String, lang:String, units:String): WeatherResponse
}