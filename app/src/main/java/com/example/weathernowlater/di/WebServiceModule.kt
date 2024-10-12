package com.example.weathernowlater.di

import com.example.data.currentweather.webservice.CurrentWeatherWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebServiceModule {

    @Singleton
    @Provides
    fun provideCurrentWeatherWebService (retrofit: Retrofit): CurrentWeatherWebService {
        return retrofit.create(
            CurrentWeatherWebService::class.java
        )
    }
}