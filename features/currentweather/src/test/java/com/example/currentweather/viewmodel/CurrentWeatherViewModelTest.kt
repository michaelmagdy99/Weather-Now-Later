package com.example.currentweather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.uistate.UIState
import com.example.currentweather.model.toUI
import com.example.domain.currentweather.model.WeatherDomainModel
import com.example.domain.currentweather.usecases.GetCurrentWeatherUseCase
import com.example.domain.location.usecase.GetLocationUseCase
import com.example.domain.currentweather.usecases.GetWeatherForCity
import com.example.domain.location.model.LocationDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class CurrentWeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Mock
    lateinit var getCurrentLocation: GetLocationUseCase
    @Mock
    lateinit var getWeatherForCity: GetWeatherForCity

    private lateinit var viewModel: CurrentWeatherViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CurrentWeatherViewModel(
            getCurrentWeatherUseCase,
            getCurrentLocation,
            getWeatherForCity
        )
    }


    @Test
    fun `fetchWeather should emit success state when data is retrieved`() = runTest {
        // Given
        val mockLocation = mock<LocationDomainModel>()
        val mockWeather = mock<WeatherDomainModel>()

        whenever(getCurrentLocation.invoke()).thenReturn(mockLocation)

        whenever(getCurrentWeatherUseCase.invoke(
            lat = mockLocation.latitude,
            lon = mockLocation.longitude,
            lang = "en",
            units = "metric",
            appId = any()
        )).thenReturn(flowOf(mockWeather))

        // when
        viewModel.fetchWeather()

        // THen
        assertEquals(UIState.Success(mockWeather.toUI()), viewModel.uiState.value)
    }

    @Test
    fun `fetchWeather should emit error state when an exception occurs`() = runTest {
        // Given
        val mockLocation = mock<LocationDomainModel>()
        whenever(getCurrentLocation.invoke()).thenReturn(mockLocation)
        whenever(getCurrentWeatherUseCase(
            lat = mockLocation.latitude,
            lon = mockLocation.longitude,
            lang = "en",
            units = "metric",
            appId = any()
        )).thenThrow(RuntimeException("Network Error"))

        // When
        viewModel.fetchWeather()

        // then
        assertEquals(UIState.Error("Network Error"), viewModel.uiState.value)
    }

    @Test
    fun `fetchWeatherForCity should emit success state when data is retrieved`() = runTest {
        // Given
        val cityName = "London"
        val mockWeather = mock<WeatherDomainModel>()
        whenever(getWeatherForCity.invoke(cityName, "en", "metric", any())).thenReturn(flowOf(mockWeather))

        // When
        viewModel.fetchWeatherForCity(cityName)

        // then
        assertEquals(UIState.Loading, viewModel.uiState.value)
        assertEquals(UIState.Success(mockWeather.toUI()), viewModel.uiState.value)
    }

    @Test
    fun `fetchWeatherForCity should emit error state when an exception occurs`() = runTest {
        // Given
        val cityName = "London"
        whenever(getWeatherForCity.invoke(cityName, "en", "metric", any())).thenThrow(RuntimeException("Network Error"))

        // When
        viewModel.fetchWeatherForCity(cityName)

        // then
        assertEquals(UIState.Loading, viewModel.uiState.value)
        assertEquals(UIState.Error("Network Error"), viewModel.uiState.value)
    }

}
