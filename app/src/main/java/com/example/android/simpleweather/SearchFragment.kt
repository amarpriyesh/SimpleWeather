package com.example.android.simpleweather

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.simpleweather.databinding.FragmentSearchBinding
import com.example.android.simpleweather.dto.DayForecast
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.round

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class SearchFragment: Fragment() {
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getDate(epochSeconds: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochSeconds * 1000), ZoneId.systemDefault())
    }

    private fun getTimeString(epochSeconds: Long): String {
        return getDate(epochSeconds).format(DateTimeFormatter.ofPattern("HH:mm:ss"))
    }

    private fun getDateString(epochSeconds: Long): String {
        return getDate(epochSeconds).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
    }

    private fun getForecast(forecast: DayForecast): String {
        return "Forecast for ${getDateString(forecast.dt)}:\nSunrise: ${getTimeString(forecast.sunrise)}\nSunset: ${getTimeString(forecast.sunset)}\nHigh temperature: ${kelvinToFahrenheit(forecast.temperatureRange.max)} °F\nLow temperature: ${kelvinToFahrenheit(forecast.temperatureRange.min)} °F"
    }

    private fun kelvinToFahrenheit(kelvin: Double): Int {
        return round((kelvin - 273.15) * (9.0 / 5.0) + 32.0).toInt()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.forecast.observe(viewLifecycleOwner) {
            println("forecast: $it")
            binding.locationTv.text = "${it.latitude}, ${it.longitude}"
            binding.forecastTv.text =
                it.daily.subList(0, 5).joinToString("\n\n") { daily -> getForecast(daily) }
        }

        binding.searchButton.setOnClickListener {
            println("Searched")
            val locationInfo = ZipCodeReader(resources).getLatLong(binding.zipCodeEditText.text.toString())
            viewModel.getWeatherData(locationInfo!!.latitude, locationInfo.longitude)
        }
    }
}
