package com.example.miincidencia.Dataclases

import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.util.Date

data class IncidenciaDataResponse(
    @SerializedName("num") val num: Long,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("subtipo_id") val subtipo: incidenciasSubtipoDataResponse,//subtipo
    @SerializedName("fecha_creacion") val fecha_creacion: Date,
    @SerializedName("fecha_cierre") val fecha_cierre: Date,
    @SerializedName("descripcion") val Descripcion: String,
    @SerializedName("estado") val Estado: String,
    @SerializedName("adjunto_url") val adjunto_url: String,
    @SerializedName("creadorId") val Creador:personalDataResponse,//personal
    @SerializedName("responsable_id") val responsable: personalDataResponse,//personal
    @SerializedName("equipoId") val equipo: equipoDataResponse,//equipo
    @SerializedName("tiempo") val Tiempo: Time,

    )
