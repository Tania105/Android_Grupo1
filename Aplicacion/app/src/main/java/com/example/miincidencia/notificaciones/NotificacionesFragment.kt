package com.example.miincidencia.notificaciones

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
import com.example.miincidencia.R
import com.example.miincidencia.adapters.MyFragmentPagerAdapter
import com.example.miincidencia.configuracion.ConfiguracionActivity
import com.example.miincidencia.databinding.FragmentListaIncidenciasBinding
import com.example.miincidencia.databinding.FragmentNotificacionesBinding
import com.example.miincidencia.incidencia.IncidenciaActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificacionesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificacionesFragment : Fragment() {
    lateinit var binding: FragmentNotificacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificacionesBinding.inflate(inflater, container, false)
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
        val activity = requireActivity() as AppCompatActivity

        activity.supportActionBar?.apply{
            title = "Notificaciones"
        }
    }

}