package com.example.miincidencia.listadoIncidencias

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.miincidencia.incidencia.IncidenciaActivity
import com.example.miincidencia.adapters.MyFragmentPagerAdapter
import com.example.miincidencia.R
import com.example.miincidencia.configuracion.ConfiguracionActivity
import com.example.miincidencia.databinding.FragmentListaIncidenciasBinding

class ListaIncidenciasFragment : Fragment() {

    lateinit var binding: FragmentListaIncidenciasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}
}