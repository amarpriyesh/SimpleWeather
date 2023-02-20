package com.example.android.simpleweather;

public class CurrentWeatherModel {
    private final String day;
    private final WeatherType weatherType;
    private final String weatherDescription;
    private final String unit;
    private final int currentTemp;


    public CurrentWeatherModel(String day, WeatherType weatherType, String weatherDescription, String unit, int currentTemp) {
        this.day = day;
        this.weatherType = weatherType;
        this.weatherDescription = weatherDescription;
        this.unit = unit;
        this.currentTemp = currentTemp;
    }

    public String getDay() {
        return day;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getUnit() {
        return unit;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }
}

