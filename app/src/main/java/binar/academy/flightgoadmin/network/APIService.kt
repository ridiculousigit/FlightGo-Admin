package binar.academy.flightgoadmin.network

import binar.academy.flightgoadmin.model.admin.AdminDataClass
import binar.academy.flightgoadmin.model.admin.AdminResponse
import binar.academy.flightgoadmin.model.admin.Data
import binar.academy.flightgoadmin.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIService {
    //get all tiket
    @GET("ticket")
    fun getAllTic() : Call<TiketResponseItem>

    @POST("login")
    fun adminLogin(@Body login:AdminDataClass): Call<Data>

    @GET("logout")
    fun adminLogout(@Header("Authorization") token: String): Call<String>

    @GET("users")
    fun getUsers(@Header("Authorization") token: String) : Call<UserResponse>
}