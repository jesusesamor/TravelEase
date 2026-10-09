package com.example.travelease.model

import com.google.gson.annotations.SerializedName

data class VueloRequestDto(
    @SerializedName("airline") val aerolinea: String,
    @SerializedName("origin") val origen: String,
    @SerializedName("destination") val destino: String,
    @SerializedName("departure_date") val fechaSalida: String,
    @SerializedName("arrival_date") val fechaEntrada: String,
    @SerializedName("price") val precio: Double
)