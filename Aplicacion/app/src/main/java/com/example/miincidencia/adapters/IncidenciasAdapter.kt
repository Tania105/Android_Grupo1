package com.example.miincidencia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.R
import com.example.miincidencia.adapters.viewHolders.IncidenciasViewHolder


class IncidenciasAdapter(var incidenciasLista:List<IncidenciaDataResponse> = emptyList(),
                         private val  onItemSelectedListener :(Long)->Unit):
    RecyclerView.Adapter<IncidenciasViewHolder>() {

    fun updateList(nuevaLista: List<IncidenciaDataResponse>){
        this.incidenciasLista=nuevaLista
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncidenciasViewHolder {

        return IncidenciasViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_incidencia, parent, false))
    }

    override fun getItemCount()=incidenciasLista.count()


    override fun onBindViewHolder(viewholder: IncidenciasViewHolder, position: Int) {
        viewholder.bind(incidenciasLista[position], onItemSelectedListener )
    }
}