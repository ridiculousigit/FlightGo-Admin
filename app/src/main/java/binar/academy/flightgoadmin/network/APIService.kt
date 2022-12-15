package binar.academy.flightgoadmin.network

import binar.academy.flightgoadmin.model.tiket.TiketResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    //get all tiket
    @GET("ticket")
    fun getAllTic() : Call<TiketResponseItem>
}