package binar.academy.flightgoadmin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.flightgoadmin.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.network.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(var api : APIService): ViewModel() {
    //LivaData
    var getAll : MutableLiveData<TiketResponseItem?> = MutableLiveData()

    fun getLiveAllTic() : MutableLiveData<TiketResponseItem?>{
        return getAll
    }

    //Retrofit

    fun getApiAllTic(){
        api.getAllTic()
            .enqueue(object : Callback<TiketResponseItem>{
                override fun onResponse(
                    call: Call<TiketResponseItem>,
                    response: Response<TiketResponseItem>,
                ) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body != null){
                            getAll.postValue(body)
                            Log.d("SUCCESS GET : ", "$body")
                        }else{
                            getAll.postValue(null)
                            error("DATA NULL")
                        }
                    }
                }

                override fun onFailure(call: Call<TiketResponseItem>, t: Throwable) {
                    Log.e("FAILED GET : ", "SOMETHING WRONG ", t)
                }

            })
    }


}