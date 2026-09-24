package com.example.travelease.repository

import com.example.travelease.model.ClienteDto
import com.example.travelease.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GerenciaRepository {

    // AURELIO: Este método llama a la API. Coroutine en hilo IO para no trabar la pantalla.
    suspend fun obtenerTopClientes(): List<ClienteDto> {
        return withContext(Dispatchers.IO) {
            try {
                // CUANDO LA API ESTÉ LISTA, DESCOMENTA ESTA LÍNEA Y BORRA LA LISTA VACÍA:
                // RetrofitClient.apiService.getTopClientes()

                emptyList() // Retorno falso temporal para que no dé error hoy
            } catch (e: Exception) {
                // Si el servidor de Django se cae o no hay internet, devolvemos vacío
                emptyList()
            }
        }
    }
}