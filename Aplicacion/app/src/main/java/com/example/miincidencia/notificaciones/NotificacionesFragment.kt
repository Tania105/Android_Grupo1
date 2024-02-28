package com.example.miincidencia

import android.content.Context
import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.miincidencia.configuracion.ConfiguracionActivity
import com.example.miincidencia.databinding.FragmentNotificacionesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificacionesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificacionesFragment : Fragment(){
    lateinit var binding: FragmentNotificacionesBinding
    private lateinit var speechRecognizer: SpeechRecognizer
    private var listening = false

    private val PERMISSIONS_REQUEST_RECORD_AUDIO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        speechRecognizer.setRecognitionListener(recognitionListener)
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), PERMISSIONS_REQUEST_RECORD_AUDIO)
        }
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
        inflater.inflate(R.menu.menu_notificaciones, menu)
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
        val mensajeBienvenida = "¡Hola! Soy Korobot, tu asistente virtual. ¿En qué puedo ayudarte hoy?"
        addMensaje("Korobot", mensajeBienvenida, isUsuario = false)
        setHasOptionsMenu(true)
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply{
            title = "Korobot, tu asistente personal"
        }
        binding.enviarMensaje.setOnClickListener {
            val mensajeUsuario = binding.edtEscribir.text.toString().trim()
            if (mensajeUsuario.isNotEmpty()) {
                addMensaje("Usuario", mensajeUsuario, isUsuario = true)
                val respuestaAsistente = generarRespuestaAsistente(mensajeUsuario)

                addMensaje("Korobot", respuestaAsistente, isUsuario = false)
                val respuestaId = generarRespuestaAsistenteVoz(mensajeUsuario)

                // Reproducir el audio correspondiente a la respuesta
                playAudio(respuestaId)
                binding.edtEscribir.text.clear()
                hideKeyboard()
            }
        }
        binding.eliminarTodo.setOnClickListener{
            borrarConversacion(it)
        }
        binding.startListeningButton.setOnClickListener {
            // Llamar al método para iniciar el reconocimiento de voz
            stopListening()
            startListening()
        }
        
    }

    private fun addMensaje(nombre: String, mensaje: String, isUsuario: Boolean) {
        // Crear un nuevo LinearLayout para contener la imagen y el mensaje
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.HORIZONTAL

        // Crear una nueva vista para mostrar la imagen de perfil
        val profileImage = ImageView(requireContext())
        profileImage.setImageResource(R.drawable.korobot)
        val imageSize = resources.getDimensionPixelSize(R.dimen.image_size) // Definir el tamaño de la imagen
        val imageParams = LinearLayout.LayoutParams(imageSize, imageSize)
        profileImage.layoutParams = imageParams

        // Crear una nueva vista para mostrar el nombre
        val nombreTextView = TextView(requireContext())
        nombreTextView.text = nombre

        // Crear una nueva vista para mostrar el mensaje
        val mensajeTextView = TextView(requireContext())
        mensajeTextView.text = mensaje
        mensajeTextView.setPadding(20, 10, 20, 10)

        // Alinear la vista adecuadamente dentro del LinearLayout
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        if (!isUsuario) {
            layout.addView(profileImage)

            layout.addView(mensajeTextView)
            binding.contenidoConversacion.addView(layout)
        } else {
            layout.orientation = LinearLayout.VERTICAL
            layout.gravity = Gravity.END
            nombreTextView.layoutParams = layoutParams
            layout.addView(nombreTextView)
            mensajeTextView.layoutParams = layoutParams
            layout.addView(mensajeTextView)
            binding.contenidoConversacion.addView(layout)
        }

        // Hacer scroll hacia abajo para mostrar el último mensaje
        binding.scrollView.post {
            binding.scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun generarRespuestaAsistente(pregunta: String): String {
        return when {
            pregunta.contains("hola", ignoreCase = true) -> "¡Hola! Soy Korobot, tu asistente virtual. ¿En qué puedo ayudarte hoy?"
            pregunta.contains("adiós", ignoreCase = true) -> "¡Hasta luego! Si necesitas ayuda, estaré atento a usted."
            pregunta.contains("ayuda", ignoreCase = true) -> "A continuación, le paso la lista de las palabras más utilizadas que me puede comentar, espero que le sirva de utilidad     " +
                    "\n\"hola\",\n" +
                    "    \"adiós\",\n" +
                    "    \"ayuda\",\n" +
                    "    \"crear incidencia\",\n" +
                    "    \"editar incidencia\",\n" +
                    "    \"comentario\",\n" +
                    "    \"cerrar sesión\",\n" +
                    "    \"eliminar incidencia\",\n" +
                    "    \"idioma\",\n" +
                    "    \"filtrar\",\n" +
                    "    \"buscar\",\n" +
                    "    \"korobot\",\n"

            pregunta.contains("canción", ignoreCase = true) -> {
                // Implementa la lógica para contar chistes aquí
                // Puedes almacenar una lista de chistes y elegir uno al azar
                "Claro, aqui va una de las mejores canciones de todos los tiempos"
            }
            pregunta.contains("crear incidencia", ignoreCase = true) -> {
                "Por supuesto, para crear una nueva incidencia debe ir a la ventana incidencias y pulsar en el simbolo +, ahi podrá rellenar los datos que correspondan y al pulsar al check, se creará la incidencia."
            }
            pregunta.contains("editar incidencia", ignoreCase = true) -> {
                "Para poder editar una incidencia, deberá ir a la ventana incidencias, y pulsar sobre una que se encuentre en estado nuevo, una vez dentro pulse el lápiz y podrá editar los datos que necesite."
            }
            pregunta.contains("comentario", ignoreCase = true) -> {
                "Pues claro, para agregar comentarios, deberá ir a la ventana incidencias y pulse sobre una que no este cerrada. Una vez dentro, pulse mostrar más, y le saldrá una casilla para poder escribir un comentario."
            }
            pregunta.contains("cerrar sesion", ignoreCase = true) -> {
                "Para poder cerrar sesión deberá ir a configuración, que se encuentra en la parte superior derecha de la pantalla y pulsar Cerrar Sesión, podrá volver a entrar a IRIS introduciendo sus datos."
            }
            pregunta.contains("eliminar incidencia", ignoreCase = true) -> {
                "Claro, para poder borrar una incidencia, deberá ir a incidencias y pulsar sobre una que se encuentre en estado nuevo, una vez ahi pulse al contenedor y podrá borrarla. Recuerde hacer esta acción con precaución."
            }
            pregunta.contains("idioma", ignoreCase = true) -> {
                "Efectivamente, tenemos la opción de cambiar de idioma en la configuración, ahi podrá poner Iris en inglés o en español, yo solo se hablar en español, " +
                        "no me lo tenga en cuenta :D"
            }

            pregunta.contains("filtrar", ignoreCase = true) -> {
                "Iris puede filtrar sus incidencias por el tipo de cada una, y por los estados generales. Nuevas, en proceso y cerradas , en un futuro se podrían agregar muchas más si lo desean, por un coste."

            }

            pregunta.contains("buscar", ignoreCase = true) -> {
                "Si desea encontrar una incidencia, vaya a la pestaña de incidencias y pulse al botón de la lupa, una vez ahi podrá buscar por el número de incidencia, " +
                        "así que recomendamos que apunte sus incidencias para saber buscarlas :D"

            }
            pregunta.contains("korobot", ignoreCase = true) -> {
                "Ese soy yo, Korobot, tu asistente virtual, pregunteme lo que desee sobre Iris, disponible las 24 horas del día"
            }

            pregunta.contains("quién eres", ignoreCase = true) -> {
                "Soy batman, es broma, soy Korobot, tu asistente virtual, ¿en que puedo ayudar?"
            }

            pregunta.contains("pokemon", ignoreCase = true) -> {
                "Esta aplicación no tiene nada que ver con pokemon, nosotros capturamos incidencias, no pokemons"
            }

            pregunta.contains("pikachu", ignoreCase = true) -> {
                "Perdona, no conozco que es pikachu, yo soy Korobot, y no me pica nada"
            }

            else -> "Lo siento, no entendí la pregunta. ¿Puedes ser más específico? Sino sabe que preguntar, escriba ayuda y le saldrán las palabras claves para preguntarme"
        }
    }

    private fun generarRespuestaAsistenteVoz(pregunta: String): Int {
        return when {
            pregunta.contains("hola", ignoreCase = true) -> R.raw.saludo
            pregunta.contains("adiós", ignoreCase = true) -> R.raw.despedida
            pregunta.contains("ayuda", ignoreCase = true) -> R.raw.ayuda
            pregunta.contains("canción", ignoreCase = true) -> R.raw.cancion
            pregunta.contains("crear incidencia", ignoreCase = true) -> R.raw.crear_incidencia
            pregunta.contains("editar incidencia", ignoreCase = true) -> R.raw.editar
            pregunta.contains("comentario", ignoreCase = true) -> R.raw.comentario
            pregunta.contains("cerrar sesion", ignoreCase = true) -> R.raw.cerrar_sesion
            pregunta.contains("eliminar incidencia", ignoreCase = true) -> R.raw.borrar
            pregunta.contains("idioma", ignoreCase = true) -> R.raw.idioma
            pregunta.contains("filtrar", ignoreCase = true) -> R.raw.filtrar
            pregunta.contains("buscar", ignoreCase = true) -> R.raw.buscar
            pregunta.contains("korobot", ignoreCase = true) -> R.raw.korobot
            pregunta.contains("quién eres", ignoreCase = true) -> R.raw.batman
            pregunta.contains("pokemon", ignoreCase = true) -> R.raw.pokemon
            pregunta.contains("pikachu", ignoreCase = true) -> R.raw.pikachu
            else -> R.raw.error
        }
    }

    private fun playAudio(resourceId: Int) {
        val mediaPlayer = MediaPlayer.create(requireContext(), resourceId)
        mediaPlayer.start()
    }
    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.edtEscribir.windowToken, 0)
    }



    fun borrarConversacion(view: View) {
        // Detener el reconocimiento de voz si está en curso
        stopListening()

        // Eliminar todos los mensajes de la conversación
        binding.contenidoConversacion.removeAllViews()

        // Mostrar el mensaje de bienvenida de Korobot
        val mensajeBienvenida = generarRespuestaAsistente("Hola")
        addMensaje("Korobot", mensajeBienvenida, isUsuario = false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        // Detener y liberar recursos relacionados con el reconocimiento de voz
        speechRecognizer.destroy()
    }


    private fun processVoiceInput(input: String) {
        var cleanedInput = input // Variable para almacenar el input limpio
        var conCoro:String = ""
        // Verificar si el input contiene la palabra "Coro"
        if (input.contains("Coro", ignoreCase = true)) {
            // Iniciar el reconocimiento de voz nuevamente
            startListening()

            // Eliminar la primera ocurrencia de la palabra "Coro" en el input
            cleanedInput = input.replaceFirst("Coro", "", ignoreCase = true).trim()
            conCoro = cleanedInput
        } else {
            // Detener el reconocimiento de voz si no se detecta "Coro"
            stopListening()
        }

        // Envía el mensaje reconocido a Korobot solo si se detectó "Coro"
        if (conCoro.isNotEmpty()) {
            addMensaje("Usuario", cleanedInput, isUsuario = true)
            val respuestaId = generarRespuestaAsistenteVoz(cleanedInput)
            val response = generarRespuestaAsistente(cleanedInput)

            // Reproducir el audio correspondiente a la respuesta
            addMensaje("Korobot", response, isUsuario = false)

            playAudio(respuestaId)
        }
    }


    private fun startListening() {
        if (!listening) {
            Log.d("SpeechRecognition", "Starting speech recognition")
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES") // Idioma español
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo...")
            speechRecognizer.startListening(intent)
            listening = true
        }
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // Si el permiso no está concedido, solicítalo al usuario
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), PERMISSIONS_REQUEST_RECORD_AUDIO)
        } else {
            // El permiso ya está concedido, puedes proceder con tu lógica
            // Por ejemplo, inicializar el reconocimiento de voz aquí
            startListening()
        }



    }
    private fun speakOut(text: String) {
    }

    private fun stopListening() {

        if (listening) {
            Log.d("SpeechRecognition", "Stopping speech recognition")
            speechRecognizer.stopListening()
            listening = false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_RECORD_AUDIO -> {
                // Verificar si el usuario concedió el permiso
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido, puedes proceder con tu lógica
                    startListening() // Por ejemplo, iniciar el reconocimiento de voz
                } else {
                    // Permiso denegado por el usuario
                    // Aquí puedes mostrar un mensaje explicativo sobre por qué el permiso es necesario
                    // y proporcionar al usuario una forma de otorgar el permiso manualmente a través de la configuración de la aplicación
                    // Por ejemplo:
                    Toast.makeText(requireContext(), "Se requiere el permiso para grabar audio.", Toast.LENGTH_SHORT).show()
                }
            }
            // Agrega otros casos según sea necesario para manejar otros requestCode
        }
    }
    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}
        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray?) {}
        override fun onEndOfSpeech() {}
        override fun onError(error: Int) {
                Log.e(TAG, "Error during recognition: $error")
                // Aquí puedes manejar diferentes tipos de errores, por ejemplo:
                when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> {
                        Log.e(TAG, "ERROR_AUDIO: Error relacionado con el audio")
                    }
                    SpeechRecognizer.ERROR_CLIENT -> {
                        Log.e(TAG, "ERROR_CLIENT: Error en el cliente de reconocimiento de voz")

                    }
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> {
                        Log.e(TAG, "ERROR_INSUFFICIENT_PERMISSIONS: Permiso insuficiente")
                    }
                    SpeechRecognizer.ERROR_NETWORK -> {
                        Log.e(TAG, "ERROR_NETWORK: Error de red")
                    }
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> {
                        Log.e(TAG, "ERROR_NETWORK_TIMEOUT: Tiempo de espera de red agotado")
                    }
                    SpeechRecognizer.ERROR_NO_MATCH -> {
                        Log.e(TAG, "ERROR_NO_MATCH: No se encontró ninguna coincidencia")
                    }
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> {
                        Log.e(TAG, "ERROR_RECOGNIZER_BUSY: El reconocedor de voz está ocupado")
                    }
                    SpeechRecognizer.ERROR_SERVER -> {
                        Log.e(TAG, "ERROR_SERVER: Error en el servidor de reconocimiento de voz")
                    }
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> {
                        Log.e(TAG, "ERROR_SPEECH_TIMEOUT: Tiempo de espera de habla agotado")
                    }
                }
        }
        override fun onPartialResults(partialResults: Bundle?) {}
        override fun onEvent(eventType: Int, params: Bundle?) {}

        override fun onResults(results: Bundle?) {
            Log.d("SpeechRecognition", "Recognition results received")
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            matches?.firstOrNull()?.let {
                processVoiceInput(it)
            }

        }
    }

}
