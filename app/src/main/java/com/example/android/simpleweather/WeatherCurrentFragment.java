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


import com.example.android.simpleweather.databinding.FragmentWeatherCurrentBinding;



import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherCurrentFragment extends Fragment {

    private static final String TEMPERATURE_UNIT = "°F";

    private Utility util;

    private SearchViewModel viewModel;
    private FragmentWeatherCurrentBinding binding;
    public CurrentWeatherModel weatherModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherCurrentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        util = new Utility();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCurrentForecast().observe(getViewLifecycleOwner(), currentForecast -> {
            // TODO: Verify that weather list has at least one element
            weatherModel = new CurrentWeatherModel(util.getDateString(currentForecast.getCurrent().getDt())
            , util.weatherIdToWeatherType(currentForecast.getCurrent().getWeather().get(0).getId()), currentForecast.getCurrent().getWeather().get(0).getMain(), TEMPERATURE_UNIT, util.kelvinToFahrenheit(currentForecast.getCurrent().getTemp()));

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
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.indeterminateLoadingBar.setVisibility(View.VISIBLE);
            } else {
                binding.indeterminateLoadingBar.setVisibility(View.GONE);
            }
        });

        binding.search.setOnClickListener(v -> {
            try {
                LocationInfo locationInfo = getLocation();
                viewModel.getCurrentWeatherData(locationInfo.getLatitude(), locationInfo.getLongitude());
            }
            catch (NullPointerException e) {
                Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private LocationInfo getLocation() {
        LocationInfo locationInfo;
        try {
            locationInfo = new ZipCodeReader(getResources()).getLatLong(binding.zipCodeEditText.getText().toString());

        }

        catch (Exception e){
            throw new NullPointerException(String.format("%s is not recognized as a valid zip code",binding.zipCodeEditText.getText().toString()));


        }
        if (locationInfo==null) {
            throw new NullPointerException(String.format("%s is not recognized as a valid zip code",binding.zipCodeEditText.getText().toString()));
        }

        return locationInfo;

    }
}
