package com.example.android.simpleweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    public RecyclerView weatherRecyclerView;
    public List<WeatherModel> weatherList = new ArrayList<>();
    RecyclerView.Adapter<WeatherViewHolder> adapter;
    public Handler textHandler;
    private RunnableThread run;
    public EditText editTextTextPersonName;
    public TextView zip;
    public TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_weather);

        initializeRecyclerView();
        setOnClickListener();

        editTextTextPersonName = (EditText) findViewById(R.id.zip_code_edit_text);
        textHandler = new Handler();
        zip = (TextView) findViewById(R.id.zip_code_tv);
        loadingText = (TextView) findViewById(R.id.loadingText);
    }

    private void initializeRecyclerView() {
        weatherRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WeatherAdapter(weatherList, this);
        // Associates the adapter with the RecyclerView
        weatherRecyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(v -> {
            try {
                getWeather();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void getWeather() throws FileNotFoundException {
        run = new RunnableThread(this);
        new Thread(run).start();
    }
}
