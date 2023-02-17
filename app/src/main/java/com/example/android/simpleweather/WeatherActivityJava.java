package com.example.android.simpleweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Response;

public class WeatherActivityJava extends AppCompatActivity implements View.OnClickListener {

    public RecyclerView weatherRecyclerView;
    public List<WeatherModel> weatherList = new ArrayList<>();
    RecyclerView.Adapter adapter;

    private WeatherModel[] testArray;

    private Button search;

    private EditText editTextTextPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




                weatherList.add(new WeatherModel("12/10/2024", "Winter", "F", 50, 70));
        setContentView(R.layout.activity_weather);
        weatherRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter(weatherList, this);
        //Associates the adapter with the RecyclerView
        weatherRecyclerView.setAdapter(adapter);

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(this);
        editTextTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);



    }

    @Override
    public void onClick(View v) {
        if ( v.getId() ==  R.id.search) {
            getWeather();
        }

    }

    private void getWeather(){



        String query = editTextTextPersonName.getText().toString();

        WeatherModel[]  testArray = {new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)
                , new WeatherModel("12/10/2023", "Sunny", "F", 20, 40)};

        weatherList.addAll(Arrays.asList(testArray));
        adapter.notifyDataSetChanged();

    }
}