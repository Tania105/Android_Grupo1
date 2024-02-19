package com.example.miincidencia.inicio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.miincidencia.notificaciones.NotificacionesFragment
import com.example.miincidencia.R
import com.example.miincidencia.databinding.ActivityMainBinding
import com.example.miincidencia.listadoIncidencias.ListaIncidenciasFragment

class MainActivity : AppCompatActivity(){

    lateinit var binding : ActivityMainBinding
    lateinit var listaIncidenciasFragment : ListaIncidenciasFragment
    lateinit var bienvenidoFragment: BienvenidoFragment
    lateinit var notificacionesFragment: NotificacionesFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponents()
        initUI()

    }

    private fun initUI(){
        seleccionarToolbar()

    }

    private fun initComponents(){
        listaIncidenciasFragment = ListaIncidenciasFragment()
        bienvenidoFragment = BienvenidoFragment()
        notificacionesFragment = NotificacionesFragment()
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView,fragment)
            commit()
        }
    }
    private fun seleccionarToolbar(){
        binding.navegacion.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.incidencias -> {
                    setCurrentFragment(listaIncidenciasFragment)
                    true
                }
                R.id.home ->{
                    setCurrentFragment(bienvenidoFragment)
                    true
                }
                R.id.notificaciones ->{
                    setCurrentFragment(notificacionesFragment)
                    true
                }
                else -> {false}
            }
        }
    }


}