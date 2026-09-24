package com.example.travelease.model

data class EncuestaDto(
    val atencionAlCliente: Int,
    val facilidadReserva: Int,
    val relacionCalidadPrecio: Int,
    val calidadServicioProporcionado: Int,
    val caracteristicaFav: String,
    val informacionProporcionadaSencilla: Int,
    val recomendarAOtros: Int,
    val volverContratar: Int
)