package com.example.android.simpleweather;

import com.example.android.simpleweather.dto.CurrentForecast;
import com.example.android.simpleweather.dto.WeatherForecast;
import com.example.android.simpleweather.dto.WeatherForecastHourly;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface OpenWeatherDataSource {
    @GET("onecall")
    Call<WeatherForecast> getWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);

    @GET("onecall")
    Call<CurrentForecast> getCurrentWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);

    @GET("onecall")
    Call<WeatherForecastHourly> getWeatherHourly(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);

}
