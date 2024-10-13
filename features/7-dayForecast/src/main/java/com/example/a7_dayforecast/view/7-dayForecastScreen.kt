package com.example.a7_dayforecast.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.compant.ProgressBar
import com.example.core.uistate.UIState
import com.example.currentweather.view.DailyForecastItem
import com.example.currentweather.viewmodel.CurrentWeatherViewModel

@Composable
fun FullForecastScreen(
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF063970), Color(0xFF1E3A78)),
                )
            ).padding(16.dp)
    ) {
        when (uiState) {
            is UIState.Loading -> ProgressBar()
            is UIState.Success -> {
                val data = (uiState as UIState.Success).data.dailyForecasts

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data) { forecast ->
                        DailyForecastItem(forecast)
                    }
                }
            }
            is UIState.Error -> {
                BasicText(
                    text = "Error: ${(uiState as UIState.Error).message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
