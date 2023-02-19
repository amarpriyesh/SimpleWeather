package com.example.android.simpleweather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitClientInstance {

    private static Retrofit _retrofit = null;

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";



    public static Retrofit getRetrofit() {
        if (_retrofit == null) {
            _retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return _retrofit;
    }
}
