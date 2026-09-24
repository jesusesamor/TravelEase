package com.example.travelease.model

data class LoginResponse(
    val token: String,
    val username: String,
    val rol: String
)