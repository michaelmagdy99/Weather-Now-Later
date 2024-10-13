package com.example.data.currentweather.webservice

import com.example.data.currentweather.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherWebService {

    @GET("onecall?")
    suspend fun getCurrentWeather(@Query("lat") lat: Double,
                                  @Query("lon") lon: Double,
                                  @Query("appid") appId:String,
                                  @Query("lang") lang:String,
                                  @Query("units") units:String): WeatherResponse


    @GET("onecall?")
    suspend fun getWeatherForCity( @Query("appid") appId:String,
                                  @Query("lang") lang:String,
                                  @Query("units") units:String,
                                  @Query("q") cityName:String): WeatherResponse

}