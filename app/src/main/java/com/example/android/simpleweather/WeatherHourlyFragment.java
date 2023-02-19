package com.example.android.simpleweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.simpleweather.databinding.FragmentWeatherDailyBinding;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherHourlyFragment extends Fragment {

    private static final String TEMPERATURE_UNIT = "°F";
    private Utility util;

    private SearchViewModel viewModel;
    private FragmentWeatherDailyBinding binding;
    private RecyclerView.Adapter<WeatherViewHolder> adapter;
    public List<WeatherModel> weatherList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherDailyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.setContextView(getActivity());
        util = new Utility();

        return binding.getRoot();

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        viewModel.getForecastHourly().observe(getViewLifecycleOwner(), weatherForecastHourly -> {
            weatherList.clear();

            weatherList.addAll(weatherForecastHourly.getHourly().stream().map(dayForecastHourly ->
                            new WeatherModel(util.getDateStringHourly(dayForecastHourly.getDt()),

                                    util.weatherIdToWeatherType(dayForecastHourly.getWeather().get(0).getId()),
                                    dayForecastHourly.getWeather().get(0).getMain(),
                                    TEMPERATURE_UNIT,
                                    util.kelvinToFahrenheit(dayForecastHourly.getTemperature()),
                                    util.kelvinToFahrenheit(dayForecastHourly.getTemperature())))
                    .collect(Collectors.toList()));
            if (weatherList.size()<1) {
                Toast.makeText(getActivity(),"The loaction has no weather data",Toast.LENGTH_SHORT).show();
            }
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



            try {
                LocationInfo locationInfo = getLocation();
                viewModel.getWeatherData(locationInfo.getLatitude(), locationInfo.getLongitude());
            }

            catch (NullPointerException e) {
                Toast.makeText(getActivity(), e.getMessage()+" Try other zip codes",Toast.LENGTH_SHORT).show();

            }
            catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void initializeRecyclerView() {
        RecyclerView weatherRecyclerView = binding.recyclerView;
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new WeatherAdapter(weatherList, requireActivity());
        // Associates the adapter with the RecyclerView
        weatherRecyclerView.setAdapter(adapter);
    }

    private LocationInfo getLocation() {
        LocationInfo locationInfo;
        try {
            locationInfo = new ZipCodeReader(getResources()).getLatLong(binding.zipCodeEditText.getText().toString());

        }
        catch (Exception e){
            throw new NullPointerException("Zip not found");


        }
        if (locationInfo==null) {
            throw new NullPointerException("Zip not found");
        }

        return locationInfo;

    }

}
