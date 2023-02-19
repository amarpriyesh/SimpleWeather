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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getForecast().observe(getViewLifecycleOwner(), weatherForecast -> {
            weatherList.clear();
            weatherList.addAll(weatherForecast.getDaily().stream().map(dayForecast ->
                            new WeatherModel(getDateString(dayForecast.getDt()),
                                    "",
                                    "F",
                                    kelvinToFahrenheit(dayForecast.getTemperatureRange().getMin()),
                                    kelvinToFahrenheit(dayForecast.getTemperatureRange().getMax())))
                    .collect(Collectors.toList()));
            adapter.notifyDataSetChanged();
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.loadingText.setVisibility(View.VISIBLE);
            } else {
                binding.loadingText.setVisibility(View.GONE);
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
