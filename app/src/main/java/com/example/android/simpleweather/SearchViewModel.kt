package com.example.android.simpleweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.simpleweather.dto.WeatherForecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _forecast = MutableLiveData<WeatherForecast>()
    val forecast: LiveData<WeatherForecast> = _forecast

    fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _forecast.value = weatherRepository.getWeatherData(latitude, longitude)
        }
    }
}
