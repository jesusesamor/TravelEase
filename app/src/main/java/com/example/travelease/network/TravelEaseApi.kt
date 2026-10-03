package com.example.travelease.network


import com.example.travelease.model.ClienteDto
import com.example.travelease.model.ClienteRequestDto
import com.example.travelease.model.EncuestaDto
import com.example.travelease.model.LoginDto
import com.example.travelease.model.LoginResponse
import com.example.travelease.model.Reserva
import com.example.travelease.model.SugerenciaDto
import com.example.travelease.model.ValoracionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TravelEaseApi {

    @GET("api/clients-mobile/reservas/")
    suspend fun getReservas(
        @Header("Authorization") token: String
    ): Response<List<Reserva>>

    // NUEVA RUTA: Obtener la lista de clientes
    @GET("api/clients/")
    suspend fun getClientes(
        @Header("Authorization") token: String
    ): Response<List<ClienteDto>>

    @POST("api/users/login/")
    suspend fun login(
        @Body request: LoginDto
    ): Response<LoginResponse>

    @POST("api/clients-mobile/sugerencias/")
    suspend fun enviarSugerencia(
        @Header("Authorization") token: String,
        @Body sugerencia: SugerenciaDto
    ): Response<Unit>

    @POST("api/clients-mobile/encuesta-satisfaccion/")
    suspend fun postEncuestaSatisfaccion(
        @Header("Authorization") token: String,
        @Body encuesta: EncuestaDto
    ): retrofit2.Response<Void>

    @POST("api/clients-mobile/valoracion/")
    suspend fun enviarValoracionBackend(
        @Header("Authorization") token: String,
        @Body valoracion: ValoracionDto
    ): Response<Unit>
    @POST("api/clients/")
    suspend fun crearCliente(
        @Header("Authorization") token: String,
        @Body cliente: ClienteRequestDto // <-- Usamos el modelo de escritura
    ): Response<ClienteDto>
}