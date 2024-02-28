package com.example.miincidencia.adapters.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.R
import com.example.miincidencia.databinding.ItemIncidenciaBinding

class IncidenciasViewHolder(view: View) :RecyclerView.ViewHolder(view) {
    private val binding=ItemIncidenciaBinding.bind(view)
    fun bind(incidenciaDataResponse:IncidenciaDataResponse, onItemSelected: (Long) -> Unit){
        binding.txtCodigoIncidencia.text=incidenciaDataResponse.num.toString()
        binding.txtEstadoIncidencia.text=incidenciaDataResponse.Estado
        binding.txtTituloIncidencia.text=incidenciaDataResponse.tipo
        val imageView = binding.imgIncidencia

        // Supongamos que necesitas cambiar la imagen según el tipo de incidencia
        when (incidenciaDataResponse.tipo.toLowerCase()) {
            "equipos" -> {
                imageView.setImageResource(R.drawable.ordenador)
            }
            "cuentas" -> {
                imageView.setImageResource(R.drawable.cuentas)
            }
            "wifi" -> {
                imageView.setImageResource(R.drawable.wifi)
            }
            "internet" -> {
                imageView.setImageResource(R.drawable.internet)
            }
            "software" -> {
                imageView.setImageResource(R.drawable.software)
            }
            // Puedes agregar más casos según sea necesario
            else -> {
                // Si no hay coincidencia, puedes establecer una imagen predeterminada
                imageView.setImageResource(R.drawable.korobot)
            }
        }
        binding.root.setOnClickListener{onItemSelected(incidenciaDataResponse.num)}
    }
}