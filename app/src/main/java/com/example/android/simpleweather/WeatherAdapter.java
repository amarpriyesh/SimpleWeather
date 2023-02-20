package com.example.android.simpleweather;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    public List<WeatherModel> weatherList;
    public Context context;
    private Handler textHandler;

    public WeatherAdapter(List<WeatherModel> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.weather_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.day.setText(weatherList.get(position).getDay());
        holder.weatherCondition.setText(weatherList.get(position).getWeatherType().toString());
        holder.temperature.setText(String.format("%s / %s %s", weatherList.get(position).getMin(), weatherList.get(position).getMax(), weatherList.get(position).getUnit()));

        switch (weatherList.get(position).getWeatherType()) {
            case THUNDERSTORM:
                holder.img.setImageResource(R.drawable.thunderstorm);
                break;
            case DRIZZLE:
                holder.img.setImageResource(R.drawable.rain_shower);
                break;
            case RAIN:
                holder.img.setImageResource(R.drawable.rain);
                break;
            case SNOW:
                holder.img.setImageResource(R.drawable.snow);
                break;
            case ATMOSPHERE:
                holder.img.setImageResource(R.drawable.mist);
                break;
            case CLEAR:
                holder.img.setImageResource(R.drawable.clearsky);
                break;
            case CLOUDS:
                holder.img.setImageResource(R.drawable.broken_clouds);
                break;
        }


        holder.itemView.setOnClickListener(view -> {
            int min = weatherList.get(position).getMin();
            Toast.makeText(context, "Min Temperature is " + min, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
