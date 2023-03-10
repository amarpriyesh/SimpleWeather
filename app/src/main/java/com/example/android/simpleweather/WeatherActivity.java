package com.example.android.simpleweather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.android.simpleweather.databinding.ActivityWeatherBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity {


    ActivityWeatherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.daily) {
                replaceFragment(new WeatherDailyFragment());
                return true;
            } else if (item.getItemId() == R.id.hourly) {
                replaceFragment(new WeatherHourlyFragment());
                return true;
            } else if (item.getItemId() == R.id.current) {
                replaceFragment(new WeatherCurrentFragment());
                return true;
            } else {
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_weather, fragment);
        fragmentTransaction.commit();
    }
}
