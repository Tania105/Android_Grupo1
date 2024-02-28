package com.example.miincidencia.api

import ComentarioDataResponse
import SubtipoDataResponse
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.Dataclases.PerfilDataResponse
import com.example.miincidencia.Dataclases.incidenciasSubtipoDataResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("incidencias")
    suspend fun getIncidencias():Response<List<IncidenciaDataResponse>>

    @GET("incidencias/tipo={tipo}")
    suspend fun getIncidenciasByTipo(@Path("tipo") tipo: String): Response<List<IncidenciaDataResponse>>

    @GET("incidencias/creadorId/{id}")
    suspend fun getPorCreador(@Query("id")id:Long):Response<List<IncidenciaDataResponse>>

    @GET("perfiles/validacion")
    suspend fun getPerfilByEducantabriaAndPass(@Query("educantabria") educantabria:String,@Query("password") password:String):Response<PerfilDataResponse>

    @GET("incidencias/{id}")
    suspend fun getIncidenciaById(@Path("id") id: String): Response<IncidenciaDataResponse>

    @GET("incidenciasSubtipos")
    suspend fun getIncidenciasSubtipos(): Response<List<SubtipoDataResponse>>

    @GET("incidencias/activas")
    suspend fun getActiveIncidencias(): Response<List<IncidenciaDataResponse>>

    @GET("incidencias/estado")
    suspend fun getPorEstado(@Query("parametros") estado: String): Response<List<IncidenciaDataResponse>>

    @POST("incidencias")
    suspend fun createIncidencia(@Body incidencia: IncidenciaRequest): Response<IncidenciaDataResponse>

    @PUT("incidencias/{id}")
    suspend fun updateIncidencia(@Path("id") id: String, @Body incidencia: IncidenciaRequest): Response<IncidenciaDataResponse>

    @DELETE("incidencias/{id}")
    suspend fun deleteIncidencia(@Path("id") id: Long): Response<Unit>

    @GET("comentarios")
    suspend fun getComentarios(): Response<List<ComentarioDataResponse>>

    @POST("comentarios")
    suspend fun crearComentario(@Body comentario: ComentarioDataResponse): Response<ComentarioDataResponse>

    @GET("incidenciasSubtipos")
    suspend fun getSubtipos():Response<List<incidenciasSubtipoDataResponse>>

}