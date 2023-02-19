package com.example.android.simpleweather;

public class WeatherModel {
    private final String day;
    private final WeatherType weatherType;
    private final String weatherDescription;
    private final String unit;
    private final int min;
    private final int max;

    private final int currentTemp;


    public WeatherModel(String day, WeatherType weatherType, String weatherDescription, String unit, int min, int max) {
        this.day = day;
        this.weatherType = weatherType;
        this.weatherDescription = weatherDescription;
        this.unit = unit;
        this.min = min;
        this.max = max;
        this.currentTemp = 0;
    }

    public WeatherModel(String day, WeatherType weatherType, String weatherDescription, String unit, int currentTemp) {
        this.day = day;
        this.weatherType = weatherType;
        this.weatherDescription = weatherDescription;
        this.unit = unit;
        this.currentTemp = currentTemp;
        this.min = 0;
        this.max = 0;
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

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }
}
