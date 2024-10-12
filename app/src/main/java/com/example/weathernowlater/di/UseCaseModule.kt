package com.example.weathernowlater.di

import com.example.domain.currentweather.repository.ICurrentWeatherRepo
import com.example.domain.currentweather.usecases.GetCurrentWeatherUseCase
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
}