package com.example.miincidencia.Dataclases

import com.google.gson.annotations.SerializedName



data class PerfilDataResponse(
    @SerializedName("personal_id") val personal_id: Long,
    @SerializedName("dominio") val name: String,
    @SerializedName("educantabria") val educantabria: String,
    @SerializedName("password") val password: String,
    @SerializedName("perfil") val perfil: String,
    ){

}


