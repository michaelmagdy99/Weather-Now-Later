package com.example.domain.currentweather.usecases

import com.example.domain.currentweather.model.WeatherDomainModel
import com.example.domain.currentweather.repository.ICurrentWeatherRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCurrentWeatherUseCase(
    private val currentWeatherRepo: ICurrentWeatherRepo
) {
    operator fun invoke(lat: Double, lon: Double, appId: String, lang: String, units: String): Flow<WeatherDomainModel> = flow {
        val weather = currentWeatherRepo.getCurrentWeather(lat, lon, appId, lang, units)
        emit(weather)
    }
}
