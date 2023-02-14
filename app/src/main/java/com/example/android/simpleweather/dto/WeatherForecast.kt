package com.example.android.simpleweather.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
    @SerializedName("daily") val daily: List<DayForecast>,
    @SerializedName("weather") val weatherConditions: List<WeatherCondition>
)

data class DayForecast(
    @SerializedName("dt") val dt: Long,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("temp") val temperatureRange: TemperatureRange
)

data class TemperatureRange(
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
    @SerializedName("morn") val morn: Double,
    @SerializedName("day") val day: Double,
    @SerializedName("eve") val eve: Double,
    @SerializedName("night") val night: Double
)

data class WeatherCondition(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String
)
