package com.example.miincidencia.listadoIncidencias

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.MainActivity
import com.example.miincidencia.adapters.IncidenciasAdapter
import com.example.miincidencia.databinding.FragmentCerradoBinding
import com.example.miincidencia.incidencia.IncidenciaActivity
import com.example.miincidencia.incidencia.IncidenciaIniActivity
import java.lang.Thread.sleep

/**
 * A simple [Fragment] subclass.
 * Use the [CerradoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CerradoFragment : Fragment() {
    lateinit var binding: FragmentCerradoBinding

    lateinit var adapter: IncidenciasAdapter
    var lista:List<IncidenciaDataResponse> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter=IncidenciasAdapter{irAIncidencia(it)}
        Log.i("cuantas veces pasa?" ,"pocas")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCerradoBinding.inflate(inflater, container, false)
        // Inflar el layout del fragmento
        initUi()


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lista= MainActivity.Companion.lista.filter { c->
            c.Estado.toString().equals("cerrada")
        }
        cargarIncidencias()
    }
    private fun initUi(){
        setHasOptionsMenu(true)
        // Configurar la barra de acciones para mostrar el botón de "volver"
        val activity = requireActivity() as AppCompatActivity
        binding.listaIncidencias.layoutManager= LinearLayoutManager(this.context)
        binding.listaIncidencias.adapter=adapter
    }
    fun cargarIncidencias() {

        adapter.updateList(lista)

        sleep(50)

    }
    fun irAIncidencia(inci:Long){
        val intent = Intent(activity?.baseContext, IncidenciaIniActivity::class.java)


        intent.putExtra("posicion", inci)
        sleep(50)
        startActivity(intent)

    }
}