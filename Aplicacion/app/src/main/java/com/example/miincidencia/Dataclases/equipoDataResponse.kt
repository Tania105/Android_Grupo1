package com.example.miincidencia.Dataclases

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class equipoDataResponse (
    @SerializedName("id") val id: Long,
    @SerializedName("tipo_equipo") val tipo_equipo: String,
    @SerializedName("fecha_adquisicion") val fecha_adquisicion: Date,
    @SerializedName("etiqueta") val etiqueta: String,
    @SerializedName("marca") val marca: String,
    @SerializedName("modelo") val modelo: String,
    @SerializedName("Descripcion") val Descripcion: String,
    @SerializedName("baja") val baja: Int,
    @SerializedName("aula_num") val aula_num : Long,
    @SerializedName("puesto") val puesto: Int)