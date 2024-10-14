package com.example.domain.currentweather.usecases

import com.example.domain.currentweather.model.WeatherDomainModel
import com.example.domain.currentweather.repository.ICurrentWeatherRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetCurrentWeatherUseCaseTest {

    @Mock
    private lateinit var currentWeatherRepo: ICurrentWeatherRepo

    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(currentWeatherRepo)
    }

    @Test
    fun `invoke returns WeatherDomainModel when successful`() = runBlocking {
        // Given
        val lat = 12.34
        val lon = 56.78
        val appId = "testAppId"
        val lang = "en"
        val units = "metric"
        val expectedWeatherDomainModel = WeatherDomainModel(
            current = null,
            dailyForecasts = emptyList(),
            alerts = emptyList(),
            lat = lat,
            long = lon
        )

        // When
        whenever(currentWeatherRepo.getCurrentWeather(lat, lon, appId, lang, units)).thenReturn(expectedWeatherDomainModel)

        // Then
        getCurrentWeatherUseCase(lat, lon, appId, lang, units).collect { result ->
            assertEquals(expectedWeatherDomainModel, result)
        }
    }
}
