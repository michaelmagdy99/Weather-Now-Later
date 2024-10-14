package com.example.data.currentweather.repository

import com.example.data.currentweather.datasource.ICurrentWeatherRemoteDataSource
import com.example.data.currentweather.mapper.toDomain
import com.example.data.currentweather.model.WeatherResponse
import com.example.domain.currentweather.model.WeatherDomainModel
import com.example.domain.currentweather.repository.ICurrentWeatherRepo
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class CurrentWeatherRepoTest {

    @Mock
    lateinit var currentWeatherRemoteDataSource: ICurrentWeatherRemoteDataSource

    private lateinit var currentWeatherRepo: ICurrentWeatherRepo

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        currentWeatherRepo = CurrentWeatherRepo(currentWeatherRemoteDataSource)
    }

    @Test
    fun `getCurrentWeather returns WeatherDomainModel when successful`() = runTest {
        // Given
        val lat = 12.34
        val lon = 56.78
        val appId = "testAppId"
        val lang = "en"
        val units = "metric"

        val weatherResponse = mock<WeatherResponse>()
        val expectedDomainModel = mock<WeatherDomainModel>()

        // When
        whenever(currentWeatherRemoteDataSource.getCurrentWeather(lat, lon, appId, lang, units)).thenReturn(weatherResponse)
        whenever(weatherResponse.toDomain()).thenReturn(expectedDomainModel)

        val result = currentWeatherRepo.getCurrentWeather(lat, lon, appId, lang, units)

        // Then
        assertEquals(expectedDomainModel, result)
    }

    @Test
    fun `getWeatherForCity returns WeatherDomainModel when successful`() = runTest {
        // Given
        val cityName = "Test City"
        val appId = "testAppId"
        val lang = "en"
        val units = "metric"

        val weatherResponse = mock<WeatherResponse>()
        val expectedDomainModel = mock<WeatherDomainModel>()

        // When
        whenever(currentWeatherRemoteDataSource.getWeatherCity(cityName, appId, lang, units)).thenReturn(weatherResponse)
        whenever(weatherResponse.toDomain()).thenReturn(expectedDomainModel)

        val result = currentWeatherRepo.getWeatherForCity(cityName, appId, lang, units)

        // Then
        assertEquals(expectedDomainModel, result)
    }
}
