package com.example.miincidencia.incidencia


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.MainActivity
import com.example.miincidencia.R
import com.example.miincidencia.api.ApiService
import com.example.miincidencia.databinding.ActivityIncidenciaIniBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale


class IncidenciaIniActivity : AppCompatActivity() {
    lateinit var binding: ActivityIncidenciaIniBinding
    var posi:Long=0
    var inci: IncidenciaDataResponse?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncidenciaIniBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.posi=intent.getLongExtra("posicion", 0)


        // Habilita el botón de retroceso en la barra de acción
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.apply{
            title = "Modificar Incidencia"
        }
    initUI()
    }

    private fun initUI(){
        inci= MainActivity.Companion.lista.find { c->
            c.num== posi
        }
        binding.edtTituloIncidencia.setText(inci?.num.toString())
        binding.edtDescripcion.setText(inci?.Descripcion.toString())
        binding.spTipo.text=inci?.tipo.toString()
        binding.spSubTipo.text=inci?.subtipo?.sub_subtipo
        val fechaInicio = inci?.fecha_creacion
        val formatoFecha = SimpleDateFormat("dd-MM-YYYY", Locale.getDefault())
        val fechaFormateada = formatoFecha.format(fechaInicio)
        binding.txtFecha.text = fechaFormateada

        mostrarMas()
    }

    private fun mostrarMas(){
        binding.btnMostrar.setOnClickListener {

            if (binding.layoutComentarios.visibility == View.VISIBLE) {
                binding.txtAdjuntos.visibility = View.GONE
                binding.listaAdjuntos.visibility = View.GONE
                binding.txtComentarios.visibility = View.GONE
                binding.cardAdjuntos.visibility = View.GONE
                binding.btnAnadirCom.visibility = View.GONE
                binding.layoutComentarios.visibility = View.GONE
                binding.btnMostrar.text = "Mostrar más"
            } else {
                binding.txtAdjuntos.visibility = View.VISIBLE
                binding.listaAdjuntos.visibility = View.VISIBLE
                binding.txtComentarios.visibility = View.VISIBLE
                binding.cardAdjuntos.visibility = View.VISIBLE
                binding.btnAnadirCom.visibility = View.VISIBLE
                binding.layoutComentarios.visibility = View.VISIBLE
                binding.btnMostrar.text = "Mostrar menos"
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aceptar -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Aviso")
                builder.setMessage("¿Quiere guardar los cambios realizados?")
                builder.setPositiveButton("Si") { dialog, which ->
                    finish()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    // Código para manejar la acción al presionar el botón "Cancelar"
                }
                val dialog = builder.create()
                dialog.show()
                true
            }
            R.id.eliminar -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Aviso")
                builder.setMessage("¿Quiere eliminar la incidencia?")
                builder.setPositiveButton("Si") { dialog, which ->
                    eliminarIncidencia()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    // Código para manejar la acción al presionar el botón "Cancelar"
                }
                val dialog = builder.create()
                dialog.show()
                true
            }
            android.R.id.home -> {
                // Maneja el botón de retroceso
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Aviso")
                builder.setMessage("¿Quiere descartar los cambios?")
                builder.setPositiveButton("Si") { dialog, which ->
                    finish()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    // Código para manejar la acción al presionar el botón "Cancelar"
                }
                val dialog = builder.create()
                dialog.show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FICHERO && resultCode == Activity.RESULT_OK) {
            // Obtener la URI del archivo seleccionado
            val uri: Uri? = data?.data
            // Ahora puedes utilizar la URI para trabajar con el archivo seleccionado

        }
    }
    fun eliminarIncidencia(){
        val retrofit = getRetrofit()


        CoroutineScope(Dispatchers.IO).launch {
            try {
            val MyResponse = retrofit.create(ApiService::class.java).deleteIncidencia(inci!!.num)
            if (MyResponse.isSuccessful){
                Log.i("Eliminacion Incidencias", "Eliminacion Exitosa")
            }

            } catch (e: Exception) {
                // Error al enviar la solicitud
                // Manejar el error de la solicitud
            }

        }
    }
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8089/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    companion object{
        val FICHERO = 0
    }
}