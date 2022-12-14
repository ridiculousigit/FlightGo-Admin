package binar.academy.flightgoadmin.model.admin


import com.google.gson.annotations.SerializedName

data class AdminResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)