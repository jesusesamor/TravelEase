package com.example.travelease.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // AURELIO: Esta es la IP estándar del emulador para conectarse al localhost de tu PC.
    // Si vas a probar en un celular físico, cambia esto por la IPv4 de tu computadora.
    // Cuando el backend esté subido a la nube, pon la URL de producción aquí.
    private const val BASE_URL = "http://172.25.50.12:8000/"

    val apiService: TravelEaseApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convierte el JSON a Kotlin
            .build()
            .create(TravelEaseApi::class.java)
    }

}