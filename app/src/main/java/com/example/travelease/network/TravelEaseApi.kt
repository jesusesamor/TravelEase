package com.example.travelease.network

import com.example.travelease.model.ClienteDto
import com.example.travelease.model.ClienteRequestDto
import com.example.travelease.model.EncuestaDto
import com.example.travelease.model.LoginDto
import com.example.travelease.model.LoginResponse
import com.example.travelease.model.Reserva
import com.example.travelease.model.SugerenciaDto
import com.example.travelease.model.ValoracionDto
import com.example.travelease.model.Vuelo // <-- Nueva importación
import com.example.travelease.model.VueloUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    // Petición para ACTUALIZAR un cliente (usamos PUT o PATCH según lo que acepte tu Django)
    @PUT("api/clients/{id}/")
    suspend fun actualizarCliente(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body cliente: ClienteRequestDto
    ): retrofit2.Response<ClienteDto>

    //  NUEVA RUTA: Obtener la lista de vuelos (Aurelio - Bruno)
    @GET("api/services/flights/")
    suspend fun obtenerVuelos(
        @Header("Authorization") token: String
    ): Response<List<Vuelo>>

    //  CREAR UN VUELO NUEVO
    @POST("api/services/flights/")
    suspend fun crearVuelo(
        @Header("Authorization") token: String,
        @Body vuelo: com.example.travelease.model.VueloRequestDto
    ): Response<Vuelo>

    //  ACTUALIZAR UN VUELO EXISTENTE (Completo)
    @PUT("api/services/flights/{id}/")
    suspend fun actualizarVuelo(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body vuelo: com.example.travelease.model.VueloRequestDto
    ): Response<Vuelo>

    //  ENDPOINT DE PRECIOS QUE EXPLICÓ AURELIO
    @POST("api/services/flights/{id}/prices/")
    suspend fun actualizarPrecioVuelo(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body priceMap: Map<String, Double>
    ): Response<Void>

    //  ACTUALIZACIÓN GENERAL SIN PRECIO (Usando DTO tipado para evitar el error de comodines en Retrofit)
    @PUT("api/services/flights/{id}/")
    suspend fun actualizarVueloParcial(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body vuelo: VueloUpdateDto
    ): Response<Vuelo>

}