package com.example.android.simpleweather

import com.example.android.simpleweather.dto.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherDataSource {
    @GET("onecall")
    suspend fun listRepos(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("appid") apiKey: String): Response<WeatherForecast>
}
