package com.example.android.simpleweather.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
    @SerializedName("daily") val daily: List<DayForecast>,
    @SerializedName("weather") val weatherConditions: List<WeatherCondition>
)

data class WeatherForecastHourly(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
    @SerializedName("hourly") val hourly: List<DayForecastHourly>,
    @SerializedName("weather") val weatherConditions: List<WeatherCondition>
)

data class DayForecast(
    @SerializedName("dt") val dt: Long,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("temp") val temperatureRange: TemperatureRange,
    @SerializedName("weather") val weather: List<WeatherCondition>
)

data class DayForecastHourly(
    @SerializedName("dt") val dt: Long,
    @SerializedName("temp") val temperature: Double,
    @SerializedName("weather") val weather: List<WeatherCondition>
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
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String
)


data class CurrentForecast(
    @SerializedName("current") val current: CurrentCondition,
)

data class CurrentCondition(
    @SerializedName("dt") val dt: Long,
    @SerializedName("temp") val temp: Double,
    @SerializedName("weather") val weather: List<WeatherCondition>,
)
