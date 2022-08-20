package com.thakurnitin2684.codeforces.ui.main.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thakurnitin2684.codeforces.data.model.RecentSubmissions
import com.thakurnitin2684.codeforces.data.repository.ParticipationRepository
import com.thakurnitin2684.codeforces.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ParticipationViewModel @Inject constructor(private val participationRepository: ParticipationRepository) : ViewModel() {

    private val recentSubmissions = MutableLiveData<Resource<RecentSubmissions>>()



     fun fetchRecentSubmissions(handle: String) {
        recentSubmissions.postValue(Resource.loading(null))
        val response = participationRepository.getRecentSubmissions(handle)
        response.enqueue(object : Callback<RecentSubmissions> {
            override fun onResponse(call: Call<RecentSubmissions>, response: Response<RecentSubmissions>) {
                recentSubmissions.postValue(Resource.success(response.body()))
            }
            override fun onFailure(call: Call<RecentSubmissions>, t: Throwable) {
                recentSubmissions.postValue(Resource.error(t.message,null))
            }
        })
    }




    fun getRecentSubmissions(): LiveData<Resource<RecentSubmissions>> {
        return recentSubmissions
    }



}