package com.example.miincidencia.inicio

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
import com.example.miincidencia.configuracion.ConfiguracionActivity
import com.example.miincidencia.R
import com.example.miincidencia.databinding.FragmentBienvenidoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BienvenidoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BienvenidoFragment : Fragment(R.layout.fragment_bienvenido) {

    lateinit var binding: FragmentBienvenidoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBienvenidoBinding.inflate(inflater, container, false)
        // Inflar el layout del fragmento
        initUi()


        return binding.root
    }
    private fun initUi(){
        setHasOptionsMenu(true)
        // Configurar la barra de acciones para mostrar el botón de "volver"
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply{
            title = "Inicio"
        }
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
                R.id.perfil -> {
                    val intent = Intent(activity, ConfiguracionActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {false}
            }
        }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // Inflar el menú del fragmento (si es necesario)
        inflater.inflate(R.menu.menu_inicial, menu)
    }
}