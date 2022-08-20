package com.thakurnitin2684.codeforces.ui.main.viewmodel



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thakurnitin2684.codeforces.data.model.Home
import com.thakurnitin2684.codeforces.data.repository.HomeRepository
import com.thakurnitin2684.codeforces.utils.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val homeInfo = MutableLiveData<Resource<Home>>()



     fun fetchHomeInfo(handle: String) {
         Log.d("MainActivity","HVM: $handle")
        homeInfo.postValue(Resource.loading(null))
        val response = homeRepository.getHomeInfo(handle)
        response.enqueue(object : Callback<Home> {
            override fun onResponse(call: Call<Home>, response: Response<Home>) {
                homeInfo.postValue(Resource.success(response.body()))
            }
            override fun onFailure(call: Call<Home>, t: Throwable) {
                homeInfo.postValue(Resource.error(t.message,null))
            }
        })
    }


    fun getHomeInfo(): LiveData<Resource<Home>> {
        return homeInfo
    }

}