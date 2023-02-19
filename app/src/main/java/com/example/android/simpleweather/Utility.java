package com.example.android.simpleweather;



import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utility   {



    public LocalDateTime getDate(long epochSeconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochSeconds * 1000), ZoneId.systemDefault());
    }

    public String getDateString(long epochSeconds) {
        return getDate(epochSeconds).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }


    public int kelvinToFahrenheit(double kelvin) {
        return (int) Math.round((kelvin - 273.15) * (9.0 / 5.0) + 32.0);
    }

    public WeatherType weatherIdToWeatherType(int weatherId) {
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


    public String getDateStringHourly(long epochSeconds) {
        return getDate(epochSeconds).format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
    }
}
