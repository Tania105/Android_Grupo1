package com.example.miincidencia.incidencia


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.miincidencia.R
import com.example.miincidencia.databinding.ActivityIncidenciaBinding



class IncidenciaActivity : AppCompatActivity() {
    lateinit var binding: ActivityIncidenciaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncidenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Habilita el botón de retroceso en la barra de acción
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.apply{
            title = "Crear Incidencia"
        }
    initUI()
    }

    private fun initUI(){
        mostrarMas()
    }

    private fun mostrarMas(){
        binding.btnMostrar.setOnClickListener {
            // Cambiar la visibilidad de la CardView y la RecyclerView
            if (binding.txtAdjuntos.visibility == View.VISIBLE) {
                binding.txtAdjuntos.visibility = View.GONE
                binding.listaAdjuntos.visibility = View.GONE
                binding.btnMostrar.text = "Mostrar Más"
            } else {
                binding.txtAdjuntos.visibility = View.VISIBLE
                binding.listaAdjuntos.visibility = View.VISIBLE
                binding.btnMostrar.text = "Mostrar Menos"
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crear, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aceptar -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Aviso")
                builder.setMessage("¿Esta seguro de crear la incidencia?")
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
            R.id.adjuntar -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                // Permitir los tipos de archivo deseados
                intent.type = "*/*"
                intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // Excel
                    "application/msword", // Word
                    "image/png", // PNG
                    "image/jpeg", // JPG
                    "application/pdf" // PDF
                ))
                startActivityForResult(intent, FICHERO)
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

    companion object{
        val FICHERO = 0
    }
}