package com.example.domain.currentweather.repository

import com.example.domain.currentweather.model.WeatherDomainModel


interface ICurrentWeatherRepo {
    suspend fun getCurrentWeather(lat: Double, lon: Double, appId:String, lang:String, units:String): WeatherDomainModel
}