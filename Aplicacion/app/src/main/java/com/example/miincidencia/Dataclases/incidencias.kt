package com.example.miincidencia.Dataclases

data class incidencias(var incidencias:List<IncidenciaDataResponse> = emptyList()) {
    fun nuevas(estado:String):List<IncidenciaDataResponse>{
     var filtrada:List<IncidenciaDataResponse> =  incidencias.filter{
            it.Estado.equals(estado)
        }
        return filtrada;
    }
}