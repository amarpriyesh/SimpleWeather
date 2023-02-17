package com.example.android.simpleweather;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    public TextView day;
    public TextView weatherCondition;
    public TextView unit;
    public TextView min;
    public TextView max;
    public ImageView img;


    public WeatherViewHolder(@NonNull View itemView) {
        super(itemView);
        this.day = itemView.findViewById(R.id.day);
        this.img = itemView.findViewById(R.id.imageView);
        this.weatherCondition = itemView.findViewById(R.id.weather_condition);
        this.max = itemView.findViewById(R.id.max);
        this.min = itemView.findViewById(R.id.min);
        this.unit = itemView.findViewById(R.id.unit);
    }
}
