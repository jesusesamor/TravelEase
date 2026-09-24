package com.example.travelease.model

// AURELIO: Aquí defines exactamente cómo llega el JSON desde Django.
// Si Django manda un JSON con "nombre_cliente", la variable debe llamarse igual,
// o usar @SerializedName("nombre_cliente").
data class ClienteDto(
    val id: Int = 0,
    val siglas: String = "",
    val nombre: String = "",
    val montoTotal: Double = 0.0,
    val rank: String = ""
)