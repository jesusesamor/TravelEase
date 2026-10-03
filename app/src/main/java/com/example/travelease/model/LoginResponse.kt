package com.example.travelease.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    // ESTA ES LA CLAVE: Le decimos a Kotlin que busque exactamente la palabra "token" en el JSON
    @SerializedName("token")
    val token: String?,

    @SerializedName("username")
    val username: String?,

    // Recibimos el rol exactamente como lo manda Django
    @SerializedName("rol")
    val rol: String?
)