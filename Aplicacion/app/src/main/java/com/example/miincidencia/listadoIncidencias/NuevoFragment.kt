package com.example.miincidencia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.adapters.IncidenciasAdapter
import com.example.miincidencia.databinding.FragmentNuevoBinding
import com.example.miincidencia.incidencia.IncidenciaActivity
import com.example.miincidencia.incidencia.IncidenciaIniActivity
import java.lang.Thread.sleep

class NuevoFragment : Fragment() {
    lateinit var binding: FragmentNuevoBinding
    private lateinit var adapter: IncidenciasAdapter
    var lista:List<IncidenciaDataResponse> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNuevoBinding.inflate(inflater, container, false)
        // Inflar el layout del fragmento
        initUi()


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lista=MainActivity.Companion.lista.filter { c->
            c.Estado.toString().equals("abierta")
        }
        cargarIncidencias()
    }
    private fun initUi(){
        setHasOptionsMenu(true)
        // Configurar la barra de acciones para mostrar el botón de "volver"
        val activity = requireActivity() as AppCompatActivity
        adapter= IncidenciasAdapter{
            irAIncidencia(it)
        }
        binding.listaIncidencias.layoutManager= LinearLayoutManager(this.context)
        binding.listaIncidencias.adapter=adapter

    }

    fun searchByName(query: String) {




    }

    fun irAIncidencia(inci:Long){
        val intent = Intent(requireContext(), IncidenciaIniActivity::class.java)

        intent.putExtra("posicion", inci)
        startActivity(intent)

    }
    fun cargarIncidencias() {

        adapter.updateList(lista)

        sleep(100)

    }
    }


