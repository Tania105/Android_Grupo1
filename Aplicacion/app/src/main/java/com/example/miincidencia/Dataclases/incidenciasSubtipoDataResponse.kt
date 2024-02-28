package com.example.miincidencia.Dataclases

import com.google.gson.annotations.SerializedName

data class incidenciasSubtipoDataResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("subtipoNombre") val subtipo_nombre: String,
    @SerializedName("subSubtipo") val sub_subtipo: String?,
)