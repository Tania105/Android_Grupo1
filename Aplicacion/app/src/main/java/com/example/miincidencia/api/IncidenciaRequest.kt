package com.example.miincidencia.api

import com.google.gson.annotations.SerializedName

data class IncidenciaRequest(
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("prioridad") val prioridad: String?,
    @SerializedName("estado") val estado: String,
    @SerializedName("fecha_creacion") val fechaCreacion: String, // O cualquier otro tipo de fecha que necesites
    @SerializedName("fecha_actualizacion") val fechaActualizacion: String?, // O cualquier otro tipo de fecha que necesites
    @SerializedName("creador_id") val creadorId: String,
    @SerializedName("asignado_id") val asignadoId: String?,
    @SerializedName("subtipo_id") val subtipo: Long, // Campo subtipo
    @SerializedName("adjuntos") val adjuntos: List<String> // Campo adjuntos
    // Otros campos necesarios para la creación o actualización de la incidencia
)