package com.example.android.simpleweather

import android.util.Log
import com.example.android.simpleweather.dto.WeatherForecast
import java.lang.IllegalStateException
import javax.inject.Inject

class WeatherRepository @Inject constructor() {
    private val service: OpenWeatherDataSource = RetrofitClientInstance.retrofit.create(OpenWeatherDataSource::class.java)

    suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherForecast {
        try {
            val response = service.listRepos(latitude, longitude, BuildConfig.OPEN_WEATHER_KEY)
            val body = response.body()
            if (body != null) {
                return body
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", e.message.toString())
            throw IllegalStateException(e)
        }
        throw IllegalStateException()
        // https://api.openweathermap.org/data/2.5/onecall?lat=42.1&lon=-73.1&appid=681089bf5b3e811ed4584a8f55fc5120
    }
}
