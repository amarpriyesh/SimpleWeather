package com.example.android.simpleweather;

import static dagger.hilt.android.internal.Contexts.getApplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.Format;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    public List<WeatherModel> weatherList;
    public Context context;
    private Handler textHandler;

    public WeatherAdapter (List<WeatherModel> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return (new WeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.weather_card_layout, null)));


    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {


        holder.day.setText(weatherList.get(position).getDay());
        holder.weatherCondition.setText(weatherList.get(position).getWeatherCondition());
        holder.unit.setText(weatherList.get(position).getUnit());
        holder.min.setText(Integer.toString(weatherList.get(position).getMin()));
        holder.max.setText(Integer.toString(weatherList.get(position).getMax()));


if (weatherList.get(position).getWeatherCondition()=="Winter") {
    holder.img.setImageResource(R.drawable.thunderstorm);

}else {
    holder.img.setImageResource(R.drawable.mist);
}



        holder.itemView.setOnClickListener(view -> {
            int min = weatherList.get(position).getMin();








            Toast.makeText(context, "Min Temperature  is "+min, Toast.LENGTH_SHORT).show();

        });



    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
