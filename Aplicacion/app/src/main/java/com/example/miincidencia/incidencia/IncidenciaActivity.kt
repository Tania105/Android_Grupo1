package com.example.miincidencia.incidencia


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miincidencia.FileAdapter
import com.example.miincidencia.R
import com.example.miincidencia.databinding.ActivityIncidenciaBinding
import java.io.File
import java.io.FileOutputStream
import android.util.Base64
import android.util.Log
import com.example.miincidencia.Dataclases.IncidenciaDataResponse
import com.example.miincidencia.Dataclases.PerfilDataResponse
import com.example.miincidencia.MainActivity
import com.example.miincidencia.api.ApiService
import com.example.miincidencia.api.IncidenciaRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.Date
import java.util.Locale


class IncidenciaActivity : AppCompatActivity() {
    lateinit var binding: ActivityIncidenciaBinding
    lateinit var adapter : FileAdapter
    lateinit var file: String
    var posi:Long=0
    var inci: IncidenciaDataResponse?=null
    lateinit var perfil: PerfilDataResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.posi=intent.getLongExtra("posicion", 0)
        binding = ActivityIncidenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        encontrarPerfil()
        adapter = FileAdapter(ArrayList())
        binding.listaAdjuntos.adapter = adapter
        binding.listaAdjuntos.layoutManager = LinearLayoutManager(this)
        // Habilita el botón de retroceso en la barra de acción
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.apply{
            title = "Crear Incidencia"
        }

    initUI()
    }

    private fun initUI(){
        inci= MainActivity.Companion.lista.find { c->
            c.num== posi
        }
        binding.edtDescripcion.setText(inci?.Descripcion.toString())
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
               // val tipo = binding.spTipo.selectedItem as? String

                if (binding.edtDescripcion.text.isEmpty()) {
                    binding.edtDescripcion.error = "Por favor, ingrese la descripción de la incidencia"
                    return false
                }

              /*  if(tipo == "Equipo"){
                    if (binding.edtAula.text.isEmpty()) {
                        binding.edtAula.error = "Por favor, ingrese el aula"
                        return false
                    }

                    if (binding.edtPuesto.text.isEmpty()) {
                        binding.edtPuesto.error = "Por favor, ingrese el puesto"
                        return false
                    }

                    if (binding.edtNumEtiqueta.text.isEmpty()) {
                        binding.edtNumEtiqueta.error = "Por favor, ingrese el numero de la etiqueta"
                        return false
                    }
                }*/

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Aviso")
                builder.setMessage("¿Esta seguro de crear la incidencia?")
                builder.setPositiveButton("Si") { dialog, which ->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    // Código para manejar la acción al presionar el botón "Cancelar"
                }
                val dialog = builder.create()
                dialog.show()
                val tipo = binding.spTipo.text.toString()
                val subtipo = 2
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())

                // Obtener la fecha y hora actual del calendario
                val currentTime = Calendar.getInstance().time

                // Formatear la fecha y hora actual usando el formato definido
                val formattedDate = dateFormat.format(currentTime)

                val incidencia = IncidenciaRequest(
                    descripcion = binding.edtDescripcion.text.toString(),
                    tipo = binding.spTipo.text.toString(),
                    subtipo = MainActivity.tipos[1].id,
                    asignadoId = null,
                    creadorId = perfil.personal_id.toString(),
                    estado = "abierta",
                    fechaActualizacion = null,
                    fechaCreacion = formattedDate,
                    prioridad = null,
                    // Utilizar el ID del creador obtenido del perfil
                    adjuntos = adapter.getFiles()
                    // Obtiene la lista de archivos adjuntos del adaptador
                )

                enviarIncidenciaALaAPI(incidencia)


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
            val fileUri = data?.data
            fileUri?.let { uri ->
                val selectedFilePath = obtenerRutaDeArchivo(uri)
                if (selectedFilePath != null) {
                    // Haz algo con la ruta del archivo seleccionado
                    adapter.addFilePath(selectedFilePath)
                    // Guarda la ruta del archivo seleccionado para futuras referencias si es necesario
                    file = selectedFilePath
                } else {
                    // Maneja el caso en el que no se pueda obtener la ruta del archivo
                }
            }
        }
    }
    private fun obtenerRutaDeArchivo(uri: Uri): String? {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = it.getString(columnIndex)
            }
        }
        return filePath
    }

    private fun getFileFromUri(uri: Uri): File? {
        val contentResolver = applicationContext.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val fileName = getFileNameFromUri(uri) // Obtener el nombre real del archivo

        inputStream?.use { input ->
            val file = File(cacheDir, fileName)
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
            return file
        }
        return null
    }
    private fun getFileNameFromUri(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    return it.getString(displayNameIndex)
                }
            }
        }
        return "archivo_desconocido" // Si no se puede obtener el nombre real, devuelve un nombre genérico
    }

    fun uploadFileToApi(file: File) {
        // Lee el contenido del archivo y conviértelo en un arreglo de bytes
        val fileBytes = file.readBytes()

        // Convierte el arreglo de bytes a una cadena Base64
        val base64String = Base64.encodeToString(fileBytes, Base64.DEFAULT)

        // Envía la cadena Base64 a tu API en el cuerpo de la solicitud
        // Aquí debes escribir el código para realizar la solicitud a tu API
        // por ejemplo, utilizando Retrofit, Volley, etc.
        // Asumiendo que tienes una función en tu API que acepta una cadena Base64
        // y realiza la subida del archivo, podrías hacer algo así:
        // apiService.uploadFile(base64String).enqueue(...)
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
            Thread.sleep(100)
        } else {
            // Si no se encontró ningún perfil almacenado, puedes manejar esta situación según sea necesario
            Log.d("TAG", "No se encontró ningún perfil almacenado")
        }
    }
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8089/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun enviarIncidenciaALaAPI(incidencia: IncidenciaRequest) {
        val retrofit = getRetrofit()
        val apiService = retrofit.create(ApiService::class.java)


        // Crear la solicitud de incidencia incluyendo el ID del creador
        val incidenciaRequest = IncidenciaRequest(
            descripcion = incidencia.descripcion,
            tipo = incidencia.tipo,
            subtipo = incidencia.subtipo,
            asignadoId = incidencia.asignadoId,
            creadorId = incidencia.creadorId,
            estado = incidencia.estado,
            fechaActualizacion = incidencia.fechaActualizacion,
            fechaCreacion = incidencia.fechaCreacion,
            prioridad = incidencia.prioridad,
            // Utilizar el ID del creador obtenido del perfil
            adjuntos = incidencia.adjuntos.map { fileToBase64String(it) } // Convierte los archivos a cadenas Base64

        )

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.createIncidencia(incidenciaRequest)
                if (response.isSuccessful) {
                    val incidenciaCreada = response.body()
                    MainActivity.cambios=true

                    // Haz algo con la incidencia creada
                    // Por ejemplo, muestra un mensaje de éxito, actualiza la interfaz de usuario, etc.
                    Log.d("Ayuda","Se manda la incidencia")
                } else {
                    // La solicitud no fue exitosa
                    // Manejar el error de la respuesta de la API
                }
            } catch (e: Exception) {
                // Error al enviar la solicitud
                // Manejar el error de la solicitud
            }
        }
    }
    fun fileToBase64String(filePath: String): String {
        val file = File(filePath)
        val fileBytes = file.readBytes()
        return Base64.encodeToString(fileBytes, Base64.DEFAULT)
    }

    fun obtenerFechaActual(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val fecha = Date()
        return dateFormat.format(fecha)
    }

    companion object{
        val FICHERO = 0
    }
}