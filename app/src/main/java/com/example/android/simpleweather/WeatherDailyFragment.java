package com.example.android.simpleweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.simpleweather.databinding.FragmentWeatherDailyBinding;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherDailyFragment extends Fragment {

    private static final String TEMPERATURE_UNIT = "°F";

    private SearchViewModel viewModel;
    private FragmentWeatherDailyBinding binding;
    private RecyclerView.Adapter<WeatherViewHolder> adapter;
    public List<WeatherModel> weatherList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherDailyBinding.inflate(inflater, container, false);
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

        viewModel.getForecast().observe(getViewLifecycleOwner(), weatherForecast -> {
            weatherList.clear();
            weatherList.addAll(weatherForecast.getDaily().stream().map(dayForecast ->
                            new WeatherModel(getDateString(dayForecast.getDt()),
                                    // TODO: Verify that weather list has at least one element
                                    weatherIdToWeatherType(dayForecast.getWeather().get(0).getId()),
                                    dayForecast.getWeather().get(0).getMain(),
                                    TEMPERATURE_UNIT,
                                    kelvinToFahrenheit(dayForecast.getTemperatureRange().getMin()),
                                    kelvinToFahrenheit(dayForecast.getTemperatureRange().getMax())))
                    .collect(Collectors.toList()));
            adapter.notifyDataSetChanged();
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.indeterminateLoadingBar.setVisibility(View.VISIBLE);
            } else {
                binding.indeterminateLoadingBar.setVisibility(View.GONE);
            }
        });

        initializeRecyclerView();

        binding.search.setOnClickListener(v -> {
            LocationInfo locationInfo = new ZipCodeReader(getResources()).getLatLong(binding.zipCodeEditText.getText().toString());
            if (locationInfo == null) {
                // TODO: Deal with null locationInfo
                throw new IllegalStateException();
            }
            viewModel.getWeatherData(locationInfo.getLatitude(), locationInfo.getLongitude());
        });
    }

    private void initializeRecyclerView() {
        RecyclerView weatherRecyclerView = binding.recyclerView;
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new WeatherAdapter(weatherList, requireActivity());
        // Associates the adapter with the RecyclerView
        weatherRecyclerView.setAdapter(adapter);
    }
}
