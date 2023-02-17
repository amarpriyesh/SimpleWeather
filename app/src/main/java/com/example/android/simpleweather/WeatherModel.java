package com.example.android.simpleweather;

public class WeatherModel {
    private String day;
    private String weatherCondition;
    private String unit;
    private int min;
    private int max;


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

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
