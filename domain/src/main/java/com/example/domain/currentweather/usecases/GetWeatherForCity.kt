package com.example.domain.currentweather.usecases

import com.example.domain.currentweather.model.WeatherDomainModel
import com.example.domain.currentweather.repository.ICurrentWeatherRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherForCity(
    private val currentWeatherRepo: ICurrentWeatherRepo
) {
    operator fun invoke(cityName: String, appId: String, lang: String, units: String): Flow<WeatherDomainModel> = flow {
        val weather = currentWeatherRepo.getWeatherForCity(cityName, appId, lang, units)
        emit(weather)
    }
}
