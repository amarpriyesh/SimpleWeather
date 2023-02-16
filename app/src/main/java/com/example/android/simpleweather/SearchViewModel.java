package com.example.android.simpleweather;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.simpleweather.dto.WeatherForecast;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class SearchViewModel extends ViewModel {

    private final WeatherRepository weatherRepository;
    private final MutableLiveData<WeatherForecast> _forecast = new MutableLiveData<>();

    public LiveData<WeatherForecast> getForecast() {
        return _forecast;
    }

    @Inject
    SearchViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    void getWeatherData(double latitude, double longitude) {
        weatherRepository.getWeatherData(latitude, longitude, new Callback<WeatherForecast>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecast> call, @NonNull Response<WeatherForecast> response) {
                WeatherForecast body = response.body();
                if (body != null) {
                    _forecast.setValue(body);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecast> call, @NonNull Throwable t) {
                Log.e("WeatherRepository", t.getMessage());
                throw new IllegalStateException(t);
                // TODO implement UI in case of failure
            }
        });
        // https://api.openweathermap.org/data/2.5/onecall?lat=42.1&lon=-73.1&appid=681089bf5b3e811ed4584a8f55fc5120
    }
}
