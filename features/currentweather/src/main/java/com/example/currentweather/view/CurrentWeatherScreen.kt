package com.example.currentweather.view

import android.graphics.Typeface
import android.location.Location
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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

@Composable
fun CurrentWeatherScreen(viewModel: CurrentWeatherViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    when (val state = uiState.value) {
        is UIState.Loading -> ProgressBar()
        is UIState.Success -> WeatherDisplay(state.data)
        is UIState.Error -> BasicText("Error: ${state.message}")
    }
}

@Composable
fun WeatherDisplay(response: WeatherUIModel?) {
    val location = Location("").apply {
        latitude = response?.lat ?: 0.0
        longitude = response?.long ?: 0.0
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {         
        Image(
            painter = painterResource(id = com.example.core.R.drawable.morning),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize()
        )
    }
        

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                WeatherText(
                    text = LocationUtils.getAddress(LocalContext.current, location),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                WeatherText(
                    text = "${Formatter.getDate(response?.current?.dt)} | ${Formatter.getFormattedHour(response?.current?.dt?.toLong())}",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                WeatherText(
                    text = response?.current?.condition ?: "Unknown",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Typeface.DEFAULT)
                )

                Spacer(modifier = Modifier.height(8.dp))

                WeatherText(
                    text = response?.current?.temperature ?: "--",
                    fontSize = 48.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                response?.current?.icon?.let {
                    Formatter.getWeatherImage(
                        it
                    )
                }?.let { painterResource(it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = "",
                        modifier = Modifier.size(110.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                AdditionalWeatherInfo(response?.current)

                Spacer(modifier = Modifier.height(16.dp))

                response?.dailyForecasts?.let {
                    DailyForecastSection(it)
                }
            }
        }
}

@Composable
fun DailyForecastSection(forecasts: List<DailyForecastUIModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecasts) { forecast ->
            DailyForecastItem(forecast)
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
        forecast.icon.let {
            Formatter.getWeatherImage(
                it
            )
        }.let { painterResource(it) }.let {
            Image(
                painter = it,
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        WeatherText(
                text = forecast.date,
                fontSize = 14.sp,
                fontFamily = FontFamily(Typeface.DEFAULT)
            )
        Spacer(modifier = Modifier.weight(1f))

        WeatherText(
                text = forecast.weatherCondition,
                fontSize = 14.sp,
                fontFamily = FontFamily(Typeface.DEFAULT)
            )

        Spacer(modifier = Modifier.weight(1f))

        Row{
            WeatherText(
                text = "${forecast.temperature.min} / ",
                fontSize = 12.sp,
                fontFamily = FontFamily(Typeface.DEFAULT)
            )
            WeatherText(
                text = forecast.temperature.max,
                fontSize = 12.sp,
                fontFamily = FontFamily(Typeface.DEFAULT)
            )
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
        WeatherText(
            text = value,
            fontSize = 14.sp,
            fontFamily = FontFamily(Typeface.DEFAULT)
        )
        WeatherText(
            text = label,
            fontSize = 12.sp,
            fontFamily = FontFamily(Typeface.DEFAULT)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrentWeatherScreen() {
    CurrentWeatherScreen()
}
