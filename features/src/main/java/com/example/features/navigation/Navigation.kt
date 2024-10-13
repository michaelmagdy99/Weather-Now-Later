package com.example.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a7_dayforecast.view.FullForecastScreen
import com.example.currentweather.view.CurrentWeatherScreen


sealed class NavigationRoute(val route: String) {
    object CurrentWeather : NavigationRoute("currentWeather")
    object FullForecast : NavigationRoute("fullForecast")
}



@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationRoute.CurrentWeather.route) {
        composable(NavigationRoute.CurrentWeather.route) {
            CurrentWeatherScreen(navController = navController)
        }

        composable(NavigationRoute.FullForecast.route) {
            FullForecastScreen()
        }
    }
}

