package com.example.android.simpleweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.android.simpleweather.databinding.FragmentWeatherCurrentBinding;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherCurrentFragment extends Fragment {

    private static final String TEMPERATURE_UNIT = "°F";

    private SearchViewModel viewModel;
    private FragmentWeatherCurrentBinding binding;
    public WeatherModel weatherModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherCurrentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        return binding.getRoot();
    }

    private LocalDateTime getDate(long epochSeconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochSeconds * 1000), ZoneId.systemDefault());
    }

    private String getDateString(long epochSeconds) {
        return getDate(epochSeconds).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    private int kelvinToFahrenheit(double kelvin) {
        return (int) Math.round((kelvin - 273.15) * (9.0 / 5.0) + 32.0);
    }

    private WeatherType weatherIdToWeatherType(int weatherId) {
        if (200 <= weatherId && weatherId <= 299) {
            return WeatherType.THUNDERSTORM;
        }
        if (300 <= weatherId && weatherId <= 399) {
            return WeatherType.DRIZZLE;
        }
        if (500 <= weatherId && weatherId <= 599) {
            return WeatherType.RAIN;
        }
        if (600 <= weatherId && weatherId <= 699) {
            return WeatherType.SNOW;
        }
        if (700 <= weatherId && weatherId <= 799) {
            return WeatherType.ATMOSPHERE;
        }
        if (weatherId == 800) {
            return WeatherType.CLEAR;
        }
        if (801 <= weatherId && weatherId <= 809) {
            return WeatherType.CLOUDS;
        }
        throw new IllegalArgumentException(String.format("%d is not a recognized weather ID", weatherId));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCurrentForecast().observe(getViewLifecycleOwner(), currentForecast -> {
            // TODO: Verify that weather list has at least one element
            weatherModel = new WeatherModel(getDateString(currentForecast.getCurrent().get(0).getDt()
            ), weatherIdToWeatherType(currentForecast.getWeather().get(0).getId()), currentForecast.getWeather().get(0).getMain(), TEMPERATURE_UNIT, kelvinToFahrenheit(currentForecast.getCurrent().get(0).getTemp()));
        });

        binding.day.setText(weatherModel.getDay());
        binding.weatherCondition.setText(weatherModel.getWeatherDescription());
        binding.temperature.setText(String.format("%s%s", weatherModel.getCurrentTemp(), weatherModel.getUnit()));
        switch (weatherModel.getWeatherType()) {
            case THUNDERSTORM:
                binding.imageView.setImageResource(R.drawable.thunderstorm);
                break;
            case DRIZZLE:
                binding.imageView.setImageResource(R.drawable.rain_shower);
                break;
            case RAIN:
                binding.imageView.setImageResource(R.drawable.rain);
                break;
            case SNOW:
                binding.imageView.setImageResource(R.drawable.snow);
                break;
            case ATMOSPHERE:
                binding.imageView.setImageResource(R.drawable.mist);
                break;
            case CLEAR:
                binding.imageView.setImageResource(R.drawable.clearsky);
                break;
            case CLOUDS:
                binding.imageView.setImageResource(R.drawable.broken_clouds);
                break;
        }

        viewModel.getCurrentIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.indeterminateLoadingBar.setVisibility(View.VISIBLE);
            } else {
                binding.indeterminateLoadingBar.setVisibility(View.GONE);
            }
        });

        binding.search.setOnClickListener(v -> {
            LocationInfo locationInfo = new ZipCodeReader(getResources()).getLatLong(binding.zipCodeEditText.getText().toString());
            if (locationInfo == null) {
                // TODO: Deal with null locationInfo
                throw new IllegalStateException();
            }
            viewModel.getCurrentWeatherData(locationInfo.getLatitude(), locationInfo.getLongitude());
        });
    }
}
