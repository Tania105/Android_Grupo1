package com.example.miincidencia.api

import com.google.gson.annotations.SerializedName

data class ComentarioRequest(
    @SerializedName("id") val id: Int,
    @SerializedName("texto") val texto: String,
    @SerializedName("fechahora") val fechahora: String,
    @SerializedName("incidenciaNum") val incidenciaNum: Int,
    @SerializedName("adjuntoUrl") val adjuntoUrl: String,
    @SerializedName("personal") val personal: Int
)
