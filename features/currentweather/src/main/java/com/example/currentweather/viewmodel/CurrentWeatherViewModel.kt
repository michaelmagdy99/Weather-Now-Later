package com.example.currentweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.uistate.UIState
import com.example.currentweather.model.DailyForecastUIModel
import com.example.currentweather.model.WeatherUIModel
import com.example.currentweather.model.toUI
import com.example.domain.currentweather.usecases.GetCurrentWeatherUseCase
import com.example.domain.location.usecase.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.currentweather.model.toUI
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getCurrentLocation: GetLocationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<WeatherUIModel>>(UIState.Loading)
    val uiState: StateFlow<UIState<WeatherUIModel>> get() = _uiState

    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun fetchWeather() {
            viewModelScope.launch {
                try {
                    val location = getCurrentLocation.invoke()

                    _uiState.value = UIState.Loading
                    val weather = getCurrentWeatherUseCase(
                        lat = location.latitude,
                        lon = location.longitude,
                        lang = "en",
                        units = "metric",
                        appId = "3e8a36a0cdc52b973b2703a33477b75e"
                    ).first()

                    val weatherUIModel = weather.toUI()
                    _uiState.value = UIState.Success(weatherUIModel)

                } catch (e: Exception) {
                    _uiState.value = UIState.Error(e.message ?: "Unknown error")
                }
            }
        }


    fun handleIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.LoadWeather -> fetchWeather()
            is WeatherIntent.NavigateToFullForecast -> {
                viewModelScope.launch {
                    _navigationEvent.emit("fullForecast")
                }
            }
        }
    }

}


sealed class WeatherIntent {
    object LoadWeather : WeatherIntent()
    object NavigateToFullForecast : WeatherIntent()
}
