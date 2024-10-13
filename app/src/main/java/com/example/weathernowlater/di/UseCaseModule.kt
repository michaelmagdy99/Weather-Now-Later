package com.example.weathernowlater.di

import com.example.domain.currentweather.repository.ICurrentWeatherRepo
import com.example.domain.currentweather.usecases.GetCurrentWeatherUseCase
import com.example.domain.currentweather.usecases.GetWeatherForCity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    fun provideCurrentWeatherUseCase(iCurrentWeatherRepo: ICurrentWeatherRepo) : GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(iCurrentWeatherRepo)
    }

    @Provides
    fun provideWeatherForCityUseCase(iCurrentWeatherRepo: ICurrentWeatherRepo) : GetWeatherForCity {
        return GetWeatherForCity(iCurrentWeatherRepo)
    }
}