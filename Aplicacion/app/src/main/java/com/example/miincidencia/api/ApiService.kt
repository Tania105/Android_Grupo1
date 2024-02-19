package com.example.miincidencia.api

import com.example.miincidencia.listadoIncidencias.SuperHeroDataResponse
import com.example.miincidencia.listadoIncidencias.SuperHeroDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/10229233666327556/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName:String):Response<SuperHeroDataResponse>

    @GET("/api/10229233666327556/{id}")
    suspend fun getSuperheroDetail(@Path("id") superheroId:String):Response<SuperHeroDetailResponse>

}