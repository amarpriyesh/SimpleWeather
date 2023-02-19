package com.example.android.simpleweather;

public class WeatherModel {
    private final String day;
    private final String weatherCondition;
    private final String unit;
    private final int min;
    private final int max;


    public WeatherModel(String day, String weatherCondition, String unit, int min, int max) {
        this.day = day;
        this.weatherCondition = weatherCondition;
        this.unit = unit;
        this.min = min;
        this.max = max;
    }


    public String getDay() {
        return day;
    }

    public String getWeatherCondition() {
        return weatherCondition;
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
}
