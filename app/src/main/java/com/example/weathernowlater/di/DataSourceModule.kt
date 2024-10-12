package com.example.weathernowlater.di

import com.example.data.currentweather.datasource.CurrentWeatherRemoteDataSource
import com.example.data.currentweather.datasource.ICurrentWeatherRemoteDataSource
import com.example.data.currentweather.webservice.CurrentWeatherWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun provideCurrentWeatherRemoteDataSource(weatherService: CurrentWeatherWebService): ICurrentWeatherRemoteDataSource {
        return CurrentWeatherRemoteDataSource(weatherService)
    }
}