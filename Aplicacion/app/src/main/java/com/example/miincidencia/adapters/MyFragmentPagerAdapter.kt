package com.example.miincidencia.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.miincidencia.listadoIncidencias.CerradoFragment
import com.example.miincidencia.listadoIncidencias.EnProcesoFragment
import com.example.miincidencia.listadoIncidencias.NuevoFragment

class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // Retorna el Fragment correspondiente a la posición
        return when (position) {
            0 -> NuevoFragment()
            1 -> EnProcesoFragment()
            2 -> CerradoFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    override fun getCount(): Int {
        // Retorna el número total de fragmentos
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Retorna el título de cada página (opcional)
        return when (position) {
            0 -> "Nuevo"
            1 -> "En proceso"
            2 -> "Cerrado"
            else -> null
        }
    }
}