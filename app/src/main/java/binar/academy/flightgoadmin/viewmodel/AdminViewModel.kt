package binar.academy.flightgoadmin.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import binar.academy.flightgoadmin.database.DataStoreAdmin
import binar.academy.flightgoadmin.model.admin.AdminDataClass
import binar.academy.flightgoadmin.model.admin.Data
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
    var livedataToken: MutableLiveData<String> = MutableLiveData()
    var livedataIsLogin: MutableLiveData<Boolean> = MutableLiveData()
    private val adminStore: DataStoreAdmin = DataStoreAdmin(appContext)
    var getAll : MutableLiveData<TiketResponseItem?> = MutableLiveData()
    var login : MutableLiveData<Data?> = MutableLiveData()

    fun saveData(role: String, token_: String) {
        GlobalScope.launch {
            adminStore.saveData(role, token_)
        }
    }

    fun getToken()= adminStore.getToken().asLiveData()
    fun getRole()= adminStore.getRole().asLiveData()

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

    fun LoginLive() : LiveData<Data?> {
        return login
    }

    //Retrofit
    fun apiLogin(email: String, pass: String){
        api.adminLogin(AdminDataClass(email,pass))
            .enqueue(object : Callback<Data>{
                override fun onResponse(call: Call<Data>, response: Response<Data>) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body!=null){
                            login.postValue(body)
                            Log.d("SUCCESS", "$body")
                        }else{
                            login.postValue(null)
                            error("NULL" + response.message())
                        }
                    }else{
                        login.postValue(null)
                        error(response.message())
                    }
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    login.postValue(null)
                    Log.e("FAILED", "SOMETHING WRONG", t )
                }

            })
    }

    fun getApiAllTic(token_: String){
        api.getAllTic(token_)
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