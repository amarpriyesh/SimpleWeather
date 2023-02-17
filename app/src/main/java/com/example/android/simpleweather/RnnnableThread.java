package com.example.android.simpleweather;

import android.annotation.SuppressLint;

import java.io.FileNotFoundException;
import java.util.List;

class RunnableThread implements Runnable {




    WeatherActivityJava weatherClass ;

    public RunnableThread(WeatherActivityJava weatherClass) {
        this.weatherClass = weatherClass;

    }
    @Override
    public void run() {
        List<String> sList = null;
        try {
            sList = new Rest().openConnection();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



        for(int i = 0; i<sList.size();i++) {
            try {

                int finalI = i;
                String finalS = sList.get(i);

                weatherClass.textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {

                        weatherClass.editTextTextPersonName.setText("<<<<<<<<>>>>>>");
                        weatherClass.weatherList.add(new WeatherModel(finalS, "Winter", "F", finalI, finalI+20));



                    }
                });
                Thread.sleep(30);

                weatherClass.textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {

                        weatherClass.editTextTextPersonName.setText(">>");




                    }
                });

                Thread.sleep(30);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }



            weatherClass.textHandler.post(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {

                    weatherClass.adapter.notifyDataSetChanged();
                    weatherClass.editTextTextPersonName.setText("LOADED");




                }
            });



        }







    }
}

