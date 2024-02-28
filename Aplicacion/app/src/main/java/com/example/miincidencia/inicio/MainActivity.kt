package com.example.miincidencia

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.Dataclases.PerfilDataResponse
import com.example.miincidencia.Dataclases.incidenciasSubtipoDataResponse
import com.example.miincidencia.api.ApiService
import com.example.miincidencia.databinding.ActivityMainBinding
import com.example.miincidencia.inicio.BienvenidoFragment
import com.example.miincidencia.inicio.getRetrofit
import com.example.miincidencia.listadoIncidencias.ListaIncidenciasFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    lateinit var perfil: PerfilDataResponse
    lateinit var binding: ActivityMainBinding
    lateinit var listaIncidenciasFragment: ListaIncidenciasFragment
    lateinit var bienvenidoFragment: BienvenidoFragment
    lateinit var notificacionesFragment: NotificacionesFragment
    var cambios = true
    var obtenidos=false


    lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()

        encontrarPerfil()
        buscarIncidencias()
        buscarTipos()
        initComponents()

        setCurrentFragment(bienvenidoFragment)

        initUI()

    }

    private fun encontrarPerfil(){
        // Obtener las preferencias compartidas
        val sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

        // Recuperar el perfil almacenado
        val perfilString = sharedPreferences.getString("perfil", null)
        // Verificar si se encontró un perfil almacenado
        if (perfilString != null) {
            // Si se encontró un perfil, puedes convertirlo de String a objeto PerfilDataResponse usando Gson
            val gson = Gson()
            perfil = gson.fromJson(perfilString, PerfilDataResponse::class.java)

            // Ahora puedes utilizar el perfil recuperado
            // Por ejemplo, puedes mostrar sus datos en la interfaz de usuario
            Log.d("TAG", "Perfil recuperado: $perfil")
            sleep(100)
        } else {
            // Si no se encontró ningún perfil almacenado, puedes manejar esta situación según sea necesario
            Log.d("TAG", "No se encontró ningún perfil almacenado")
        }
    }
    private fun initUI() {
        seleccionarToolbar()

    }

    private fun initComponents() {
        listaIncidenciasFragment = ListaIncidenciasFragment()
        bienvenidoFragment = BienvenidoFragment()
        notificacionesFragment = NotificacionesFragment()
        Log.i("prueba2", "salida2")
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView, fragment)
            commit()
            Log.i("prueba3", "salida3")
        }
    }

    private fun seleccionarToolbar() {
        binding.navegacion.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.incidencias -> {

                    setCurrentFragment(listaIncidenciasFragment)
                    true
                }

                R.id.home -> {

                    setCurrentFragment(bienvenidoFragment)
                    true
                }

                R.id.notificaciones -> {
                    setCurrentFragment(notificacionesFragment)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    companion object {

        var url: String? = null
        var lista:List<IncidenciaDataResponse> = emptyList()
        var tipos:List<incidenciasSubtipoDataResponse> = emptyList()
        var cambios:Boolean=true
    }

    fun buscarTipos(){
        if (!obtenidos){
            CoroutineScope(Dispatchers.IO).launch {

                var myResponse=retrofit.create(ApiService::class.java).getSubtipos()

                if (myResponse.isSuccessful){
                    var  response= myResponse.body()
                    if (response!=null){
                        tipos=response}
                }
            }}
    }
    public fun buscarIncidencias() {
        if (cambios&& perfil!=null) {
            var myResponse: Response<List<IncidenciaDataResponse>>

            CoroutineScope(Dispatchers.IO).launch {
                if(perfil?.perfil.equals("admnistrador")){
                    myResponse =
                        retrofit.create(ApiService::class.java).getIncidencias()
                }else{
                    myResponse=retrofit.create(ApiService::class.java).getPorCreador(perfil!!.personal_id.toLong())}
                if (myResponse.isSuccessful) {
                    val response: List<IncidenciaDataResponse>? = myResponse.body()
                    if (response != null) {

                        lista = response
                        Log.i("pruebas lista", lista.count().toString())
                        cambios = false
                    }
                }
            }
        }

    }
}
