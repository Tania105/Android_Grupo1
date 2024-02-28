import com.google.gson.annotations.SerializedName

data class ComentarioDataResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("texto") val texto: String,
    // Otros campos necesarios para los comentarios
)