package com.example.android.simpleweather;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.simpleweather.dto.CurrentForecast;
import com.example.android.simpleweather.dto.WeatherForecast;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class SearchViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";

    private final WeatherRepository weatherRepository;
    private final MutableLiveData<WeatherForecast> _forecast = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    private final MutableLiveData<CurrentForecast> current_forecast = new MutableLiveData<>();
    private final MutableLiveData<Boolean> current_isLoading = new MutableLiveData<>();

    public LiveData<WeatherForecast> getForecast() {
        return _forecast;
    }

    public LiveData<Boolean> getIsLoading() {
        return _isLoading;
    }

    public MutableLiveData<CurrentForecast> getCurrentForecast() {
        return current_forecast;
    }

    public MutableLiveData<Boolean> getCurrentIsLoading() {
        return current_isLoading;
    }

    @Inject
    SearchViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    void getWeatherData(double latitude, double longitude) {
        _isLoading.setValue(true);
        Log.d(TAG, String.format("Making weather data request for %s, %s", latitude, longitude));
        weatherRepository.getWeatherData(latitude, longitude, new Callback<WeatherForecast>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecast> call, @NonNull Response<WeatherForecast> response) {
                Log.d(TAG, "Got success response");
                WeatherForecast body = response.body();
                if (body != null) {
                    _forecast.setValue(body);
                }
                _isLoading.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecast> call, @NonNull Throwable t) {
                Log.d(TAG, "Got failure response");
                Log.e(TAG, t.getMessage());
                _isLoading.setValue(false);
                throw new IllegalStateException(t);
                // TODO implement UI in case of failure
            }
        });
        // https://api.openweathermap.org/data/2.5/onecall?lat=42.1&lon=-73.1&appid=681089bf5b3e811ed4584a8f55fc5120
    }

    void getCurrentWeatherData(double latitude, double longitude) {
        current_isLoading.setValue(true);
        Log.d(TAG, String.format("Making current weather data request for %s, %s", latitude, longitude));
        weatherRepository.getCurrentWeatherData(latitude, longitude, new Callback<CurrentForecast>() {
            @Override
            public void onResponse(@NonNull Call<CurrentForecast> call, @NonNull Response<CurrentForecast> response) {
                Log.d(TAG, "Got success response");
                CurrentForecast body = response.body();
                if (body != null) {
                    current_forecast.setValue(body);
                }
                current_isLoading.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<CurrentForecast> call, @NonNull Throwable t) {
                Log.d(TAG, "Got failure response");
                Log.e(TAG, t.getMessage());
                current_isLoading.setValue(false);
                throw new IllegalStateException(t);
                // TODO implement UI in case of failure
            }
        });
    }
}
