package com.example.travelease.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {


    private const val BASE_URL = "http://172.25.50.12:8000/"

    val apiService: TravelEaseApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convierte el JSON a Kotlin
            .build()
            .create(TravelEaseApi::class.java)
    }

}