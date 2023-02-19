package com.example.android.simpleweather;

import com.example.android.simpleweather.dto.CurrentForecast;
import com.example.android.simpleweather.dto.WeatherForecast;
import com.example.android.simpleweather.dto.WeatherForecastHourly;

import javax.inject.Inject;


import retrofit2.Call;
import retrofit2.Callback;


class WeatherRepository {

    @Inject
    public WeatherRepository() {}

    private final OpenWeatherDataSource service = RetrofitClientInstance.getRetrofit().create(OpenWeatherDataSource.class);

    public void getWeatherData (double latitude, double longitude, Callback<WeatherForecast> responseCallback) {

            Call<WeatherForecast> call = service.getWeather(latitude, longitude, BuildConfig.OPEN_WEATHER_KEY);
            call.enqueue(responseCallback);


    }

    public void getWeatherDataHourly (double latitude, double longitude, Callback<WeatherForecastHourly> responseCallback) {
        try {
            Call<WeatherForecastHourly> call = service.getWeatherHourly(latitude, longitude, BuildConfig.OPEN_WEATHER_KEY);
            call.enqueue(responseCallback);
        }
        catch (IllegalAccessError e) {
            new IllegalAccessError("service unavailable");
        }

        // TODO: see if exception is possible here, and deal with it if needed
    }

    public void getCurrentWeatherData(double latitude, double longitude, Callback<CurrentForecast> responseCallback) {
        Call<CurrentForecast> call = service.getCurrentWeather(latitude, longitude, BuildConfig.OPEN_WEATHER_KEY);
        call.enqueue(responseCallback);

        // TODO: see if exception is possible here, and deal with it if needed
    }
}
