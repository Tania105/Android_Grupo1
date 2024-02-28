import com.google.gson.annotations.SerializedName

data class SubtipoDataResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("subtipoNombre") val subtipoNombre: String,
    @SerializedName("subSubtipo") val subSubtipo: String
)