package com.example.currentweather.view

import android.graphics.Typeface
import android.location.Location
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.ui.compant.LoadingImage
import com.example.core.ui.compant.ProgressBar
import com.example.core.ui.compant.WeatherText
import com.example.core.uistate.UIState
import com.example.core.utilites.Formatter
import com.example.core.utilites.LocationUtils
import com.example.currentweather.model.CurrentWeatherUIModel
import com.example.currentweather.model.DailyForecastUIModel
import com.example.currentweather.model.WeatherUIModel
import com.example.currentweather.viewmodel.CurrentWeatherViewModel
import com.example.currentweather.viewmodel.WeatherIntent

@Composable
fun CurrentWeatherScreen(
    viewModel: CurrentWeatherViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { destination ->
            navController.navigate(destination)
        }
    }

    when (val state = uiState) {
        is UIState.Loading -> ProgressBar()
        is UIState.Success -> WeatherDisplay(
            response = state.data,
            onViewMoreClick = {
                viewModel.handleIntent(WeatherIntent.NavigateToFullForecast)
            }
        )
        is UIState.Error -> BasicText("Error: ${state.message}")
    }
}

@Composable
fun WeatherDisplay(
    response: WeatherUIModel?,
    onViewMoreClick: () -> Unit,
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val location = Location("").apply {
        latitude = response?.lat ?: 0.0
        longitude = response?.long ?: 0.0
    }

    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF063970), Color(0xFF1E3A78)),
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchTextField(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearchSubmit = {
                    viewModel.fetchWeatherForCity(searchQuery)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            WeatherInfo(location, response)
            Spacer(modifier = Modifier.height(16.dp))
            response?.dailyForecasts?.let { DailyForecastSection(it, onViewMoreClick) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        placeholder = { Text("Search...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Enter) {
                    onSearchSubmit()
                    true
                } else {
                    false
                }
            },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        )
    )
}



@Composable
fun WeatherInfo(location: Location, response: WeatherUIModel?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        WeatherText(text = LocationUtils.getAddress(LocalContext.current, location), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        WeatherText(text = "${Formatter.getDate(response?.current?.dt)} | ${Formatter.getFormattedHour(response?.current?.dt?.toLong())}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        WeatherText(text = response?.current?.condition ?: "Unknown", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        WeatherText(text = response?.current?.temperature ?: "--", fontSize = 48.sp)
        Spacer(modifier = Modifier.height(16.dp))

        response?.current?.icon?.let {
            Formatter.getWeatherImage(it)?.let { iconRes ->
                Image(painter = painterResource(iconRes), contentDescription = "", modifier = Modifier.size(110.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AdditionalWeatherInfo(response?.current)
    }
}

@Composable
fun DailyForecastSection(
    forecasts: List<DailyForecastUIModel>,
    onViewMoreClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(forecasts.take(3)) { forecast ->
                DailyForecastItem(forecast)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onViewMoreClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(text = "7-Day Forecast")
        }
    }
}

@Composable
fun DailyForecastItem(forecast: DailyForecastUIModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        forecast.icon?.let {
            Formatter.getWeatherImage(it)?.let { iconRes ->
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        WeatherText(text = forecast.date, fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))

        WeatherText(text = forecast.weatherCondition, fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))

        Row {
            WeatherText(text = "${forecast.temperature.min} / ", fontSize = 12.sp)
            WeatherText(text = forecast.temperature.max, fontSize = 12.sp)
        }
    }
}

@Composable
fun AdditionalWeatherInfo(weather: CurrentWeatherUIModel?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherInfoItem(iconRes = com.example.core.R.drawable.cloudy_icon, value = "${weather?.clouds ?: 0} %", label = "Clouds")
        WeatherInfoItem(iconRes = com.example.core.R.drawable.humidity_icon, value = "${weather?.humidity ?: 0} %", label = "Humidity")
        WeatherInfoItem(iconRes = com.example.core.R.drawable.wind_icon, value = "${weather?.windSpeed ?: 0} m/s", label = "Wind Speed")
        WeatherInfoItem(iconRes = com.example.core.R.drawable.visibility, value = "${weather?.visibility ?: 0} m", label = "Visibility")
    }
}

@Composable
fun WeatherInfoItem(@DrawableRes iconRes: Int, value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        WeatherText(text = value, fontSize = 14.sp)
        WeatherText(text = label, fontSize = 12.sp)
    }
}
