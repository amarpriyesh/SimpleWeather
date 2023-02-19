package com.example.android.simpleweather;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

    private static final String TAG = "SearchViewModel";

    private final WeatherRepository weatherRepository;
    private final MutableLiveData<WeatherForecast> _forecast = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<WeatherForecast> getForecast() {
        return _forecast;
    }

    public LiveData<Boolean> getIsLoading() {
        return _isLoading;
    }

    private Context context;

    @Inject
    SearchViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    void getWeatherData (double latitude, double longitude) {
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
            else{

               Toast.makeText(context,"Empty response from the server",Toast.LENGTH_SHORT).show();

            }
            _isLoading.setValue(false);
        }

        @Override
        public void onFailure(@NonNull Call<WeatherForecast> call, @NonNull Throwable t) {
            Log.d(TAG, "Got failure response");
            Log.e(TAG, t.getMessage());
            _isLoading.setValue(false);
            Toast.makeText(context,"Got failure response from the server",Toast.LENGTH_SHORT).show();
           // throw new IllegalStateException(t.getMessage());

        }
    });

        }

    public void setContextView(Context context) {
        this.context = context;
    }
    // https://api.openweathermap.org/data/2.5/onecall?lat=42.1&lon=-73.1&appid=681089bf5b3e811ed4584a8f55fc5120


}
