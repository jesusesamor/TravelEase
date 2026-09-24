package com.example.travelease.model

data class SugerenciaDto(
    val comentario: String,
    val asunto: String? = null // Opcional según lo pida tu backend
)