package com.example.android.simpleweather;

import androidx.appcompat.app.AppCompatActivity;
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

public class WeatherActivityJava extends AppCompatActivity implements View.OnClickListener {

    public RecyclerView weatherRecyclerView;
    public List<WeatherModel> weatherList = new ArrayList<>();
    RecyclerView.Adapter adapter;

    private WeatherModel[] testArray;

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    public Boolean loading;

    public Handler textHandler;

    private Button search;

    private RunnableThread run;

    public EditText editTextTextPersonName;

    public TextView zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        loading = false;
        weatherList.add(new WeatherModel("12/10/2024", "Winter", "F", 50, 70));
        setContentView(R.layout.activity_weather);
        weatherRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter(weatherList, this);
        //Associates the adapter with the RecyclerView
        weatherRecyclerView.setAdapter(adapter);

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(this);
        editTextTextPersonName = (EditText) findViewById(R.id.zip_code_edit_text);
        textHandler = new Handler();
        zip = (TextView) findViewById(R.id.zip_code_tv);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search) {
            try {
                getWeather();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void getWeather() throws FileNotFoundException {



        /*String query = editTextTextPersonName.getText().toString();

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

        weatherList.addAll(Arrays.asList(testArray));*/
       run = new RunnableThread(this);
        new Thread(run).start();




    }


}

