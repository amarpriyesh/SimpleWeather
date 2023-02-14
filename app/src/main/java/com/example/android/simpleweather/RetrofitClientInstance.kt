package com.example.android.simpleweather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private var _retrofit: Retrofit? = null
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val retrofit: Retrofit by lazy {
        if (_retrofit == null) {
            _retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        _retrofit!!
    }
}
