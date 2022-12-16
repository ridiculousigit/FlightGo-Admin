package binar.academy.flightgoadmin.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import binar.academy.flightgoadmin.database.DataStoreAdmin
import binar.academy.flightgoadmin.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.network.APIService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(var api : APIService, @ApplicationContext appContext: Context): ViewModel() {

    // LivaData
    var livedataEmail: MutableLiveData<String> = MutableLiveData()
    var livedataPassword: MutableLiveData<String> = MutableLiveData()
    var livedataIsLogin: MutableLiveData<Boolean> = MutableLiveData()
    var dataAdmin: MutableLiveData<String> = MutableLiveData()
    private val adminStore: DataStoreAdmin = DataStoreAdmin(appContext)
    var getAll : MutableLiveData<TiketResponseItem?> = MutableLiveData()

    fun callDataAdmin(lifecycle: LifecycleOwner) {
        getEmail(lifecycle)
        getPassword(lifecycle)
    }

    fun saveData(email: String, password: String) {
        GlobalScope.launch {
            adminStore.saveData(email, password)
        }
    }

    fun getEmail(lifecycle: LifecycleOwner) {
        adminStore.getEmail.asLiveData().observe(lifecycle) {
            livedataEmail.postValue(it)
        }
    }

    fun getPassword(lifecycle: LifecycleOwner) {
        adminStore.getPassword.asLiveData().observe(lifecycle) {
            livedataPassword.postValue(it)
        }
    }

    fun getUserData(lifecycle: LifecycleOwner) {
        adminStore.getDataAdmin.asLiveData().observe(lifecycle) {
            dataAdmin.postValue(it)
        }
    }

    fun checkIsLogin(lifecycle: LifecycleOwner) {
        adminStore.getIsLogin.asLiveData().observe(lifecycle) {
            livedataIsLogin.postValue(it)
        }
    }

    fun saveLoginStatus(status: Boolean) {
        GlobalScope.launch {
            adminStore.setLogin(status)
        }
    }

    fun removeLoginStatus() {
        GlobalScope.launch {
            adminStore.removeLogin()
        }
    }

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