//package com.example.android.simpleweather;
//
//import android.os.Build;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.example.android.simpleweather.databinding.FragmentSearchBinding;
//import com.example.android.simpleweather.dto.DayForecast;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.stream.Collectors;
//
//import javax.inject.Inject;
//
//import dagger.hilt.android.AndroidEntryPoint;
//
//@AndroidEntryPoint
//@RequiresApi(Build.VERSION_CODES.O)
//public class SearchFragment extends Fragment {
//    private SearchViewModel viewModel;
//
//    private FragmentSearchBinding binding;
//
////    @Inject
////    public SearchFragment() {}
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentSearchBinding.inflate(inflater, container, false);
//        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
//        return binding.getRoot();
//    }
//
//    private LocalDateTime getDate(long epochSeconds) {
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochSeconds * 1000), ZoneId.systemDefault());
//    }
//
//
//    private String getTimeString(long epochSeconds) {
//        return getDate(epochSeconds).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//    }
//
//    private String getDateString(long epochSeconds) {
//        return getDate(epochSeconds).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//    }
//
//    private String getForecast(DayForecast forecast) {
//        return String.format("Forecast for %s:\nSunrise: %s\nSunset: %s\nHigh temperature: %s °F\nLow temperature: %s °F",
//                getDateString(forecast.getDt()), getTimeString(forecast.getSunrise()), getTimeString(forecast.getSunset()), kelvinToFahrenheit(forecast.getTemperatureRange().getMax()), kelvinToFahrenheit(forecast.getTemperatureRange().getMin()));
//    }
//
//    private int kelvinToFahrenheit(double kelvin) {
//        return (int) Math.round((kelvin - 273.15) * (9.0 / 5.0) + 32.0);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        viewModel.getForecast().observe(getViewLifecycleOwner(), it -> {
//            binding.locationTv.setText(String.format("%s, %s", it.getLatitude(), it.getLongitude()));
//            binding.forecastTv.setText(
//                    it.getDaily().subList(0, 5).stream().map(this::getForecast).collect(Collectors.joining("\n\n")));
//        });
//
//        binding.searchButton.setOnClickListener(v -> {
//            LocationInfo locationInfo = new ZipCodeReader(getResources()).getLatLong(binding.zipCodeEditText.getText().toString());
//            if (locationInfo == null) {
//                // TODO: Deal with null locationInfo
//                throw new IllegalStateException();
//            }
//            viewModel.getWeatherData(locationInfo.getLatitude(), locationInfo.getLongitude());
//        });
//    }
//}
