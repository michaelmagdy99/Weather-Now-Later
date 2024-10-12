package com.example.weathernowlater.di

import com.example.data.currentweather.datasource.ICurrentWeatherRemoteDataSource
import com.example.data.currentweather.repository.CurrentWeatherRepo
import com.example.domain.currentweather.repository.ICurrentWeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideCurrentWeatherRepository(weatherRemoteDataSource: ICurrentWeatherRemoteDataSource): ICurrentWeatherRepo {
        return CurrentWeatherRepo(weatherRemoteDataSource)
    }
}