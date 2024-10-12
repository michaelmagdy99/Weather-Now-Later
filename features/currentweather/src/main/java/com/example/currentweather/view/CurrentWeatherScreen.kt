package com.example.currentweather.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.uistate.UIState
import com.example.currentweather.viewmodel.CurrentWeatherViewModel

@Composable
fun CurrentWeatherScreen(viewModel: CurrentWeatherViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp, horizontal = 24.dp)
    ){
        when (val state = uiState.value) {
            is UIState.Loading -> {
                BasicText("Loading...")
            }
            is UIState.Success -> {
                val temperature = state.data.current?.temperature
                BasicText("Current Temperature: $temperature °C")
            }
            is UIState.Error -> {
                BasicText("Error: ${state.message}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrentWeatherScreen() {
    CurrentWeatherScreen()
}
