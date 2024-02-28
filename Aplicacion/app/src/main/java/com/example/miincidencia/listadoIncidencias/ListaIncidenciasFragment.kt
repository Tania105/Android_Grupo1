package com.example.miincidencia.listadoIncidencias

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.MainActivity
import com.example.miincidencia.MyFragmentPagerAdapter
import com.example.miincidencia.NuevoFragment
import com.example.miincidencia.incidencia.IncidenciaActivity
import com.example.miincidencia.R
import com.example.miincidencia.configuracion.ConfiguracionActivity
import com.example.miincidencia.databinding.FragmentListaIncidenciasBinding

class ListaIncidenciasFragment : Fragment(){

    lateinit var binding: FragmentListaIncidenciasBinding
   var lista:List<IncidenciaDataResponse> = emptyList();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.lista= MainActivity.Companion.lista
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaIncidenciasBinding.inflate(inflater, container, false)
        // Inflar el layout del fragmento
        initUi()


        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Inflar el menú del fragmento (si es necesario)
        inflater.inflate(R.menu.menu_incidencia, menu)

        val searchItem = menu.findItem(R.id.buscador)
        val searchView = searchItem.actionView as SearchView

        // Configurar el SearchView según tus necesidades
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Aquí obtienes el texto de búsqueda y lo envías al otro fragmento
                val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragment_nuevo)
                if (fragment is NuevoFragment) {
                    fragment.searchByName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {


                return true
            }
        })

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { // Manejar clic en el botón de "volver"
                requireActivity().onBackPressed() // Volver a la actividad anterior
                true
            }R.id.perfil->{
                val intent= Intent(activity,ConfiguracionActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initUi(){
        setHasOptionsMenu(true)
        // Configurar la barra de acciones para mostrar el botón de "volver"
        val activity = requireActivity() as AppCompatActivity

        val adapter = MyFragmentPagerAdapter(childFragmentManager)

        activity.supportActionBar?.apply{
            title = "Incidencias"
        }
        binding.viewPager.adapter = adapter

        // Conectar el ViewPager con el TabLayout
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.btnFlotante.setOnClickListener{
            val intent = Intent(getActivity(), IncidenciaActivity::class.java)
            startActivity(intent)
        }



        val fEquipos = binding.fEquipos
        fEquipos.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Deselecciona todos los demás chips
                binding.fCuentas.isChecked = false
                binding.fWifi.isChecked = false
                binding.fInternet.isChecked = false
                binding.fSoftware.isChecked = false
            }
        }

        val fCuentas = binding.fCuentas
        fCuentas.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Deselecciona todos los demás chips
                binding.fEquipos.isChecked = false
                binding.fWifi.isChecked = false
                binding.fInternet.isChecked = false
                binding.fSoftware.isChecked = false
            }
        }

        val fWifi = binding.fWifi
        fWifi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Deselecciona todos los demás chips
                binding.fEquipos.isChecked = false
                binding.fCuentas.isChecked = false
                binding.fInternet.isChecked = false
                binding.fSoftware.isChecked = false
            }
        }

        val fInternet = binding.fInternet
        fInternet.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Deselecciona todos los demás chips
                binding.fEquipos.isChecked = false
                binding.fCuentas.isChecked = false
                binding.fWifi.isChecked = false
                binding.fSoftware.isChecked = false
            }
        }

        val fSoftware = binding.fSoftware
        fSoftware.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Deselecciona todos los demás chips
                binding.fEquipos.isChecked = false
                binding.fCuentas.isChecked = false
                binding.fWifi.isChecked = false
                binding.fInternet.isChecked = false
            }
        }
    }

   }

