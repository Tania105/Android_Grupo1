package com.example.miincidencia.Dataclases

import com.google.gson.annotations.SerializedName

data class personalDataResponse (
    @SerializedName("id") var id: String,
    @SerializedName("dni") var dni: String,
    @SerializedName("nombre")var nombre: String,
    @SerializedName("apellido1")var apellido1:String,
    @SerializedName("apellido2")var apellido2:String,
    @SerializedName("direccion") var direccion:String,
    @SerializedName("localidad")var localidad:String,
    @SerializedName("cp")var cp: Integer,
    @SerializedName("tlf")var tlf:String,
    @SerializedName("Activo")var activo: Boolean,
    @SerializedName("departamento_id") var departamento_id:Long
){
}