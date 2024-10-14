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

class GetWeatherForCityTest {

    @Mock
    private lateinit var currentWeatherRepo: ICurrentWeatherRepo

    private lateinit var getWeatherForCity: GetWeatherForCity

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getWeatherForCity = GetWeatherForCity(currentWeatherRepo)
    }

    @Test
    fun `invoke returns WeatherDomainModel when successful`() = runBlocking {
        // Given
        val cityName = "Test City"
        val appId = "testAppId"
        val lang = "en"
        val units = "metric"
        val expectedWeatherDomainModel = WeatherDomainModel(
            current = null,
            dailyForecasts = emptyList(),
            alerts = emptyList(),
            lat = null,
            long = null
        )

        // When
        whenever(currentWeatherRepo.getWeatherForCity(cityName, appId, lang, units)).thenReturn(expectedWeatherDomainModel)

        // Then
        getWeatherForCity(cityName, appId, lang, units).collect { result ->
            assertEquals(expectedWeatherDomainModel, result)
        }
    }
}
