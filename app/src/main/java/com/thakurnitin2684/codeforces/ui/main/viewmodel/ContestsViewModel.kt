package com.thakurnitin2684.codeforces.ui.main.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thakurnitin2684.codeforces.data.model.*
import com.thakurnitin2684.codeforces.data.repository.ContestsRepository
import com.thakurnitin2684.codeforces.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ContestsViewModel @Inject constructor(private val contestsRepository: ContestsRepository) : ViewModel() {

    private val contests = MutableLiveData<Resource<Contests>>()
    private val participatedContests = MutableLiveData<Resource<ParticipatedContests>>()



     fun fetchContests() {
        contests.postValue(Resource.loading(null))
        val response = contestsRepository.getContests()
        response.enqueue(object : Callback<Contests> {
            override fun onResponse(call: Call<Contests>, response: Response<Contests>) {
                contests.postValue(Resource.success(response.body()))
            }
            override fun onFailure(call: Call<Contests>, t: Throwable) {
                contests.postValue(Resource.error(t.message,null))
            }
        })
    }

     fun fetchParticipatedContests(handle: String) {
        participatedContests.postValue(Resource.loading(null))
        val response = contestsRepository.getParticipatedContests(handle)
        response.enqueue(object : Callback<ParticipatedContests> {
            override fun onResponse(call: Call<ParticipatedContests>, response: Response<ParticipatedContests>) {
                participatedContests.postValue(Resource.success(response.body()))
            }
            override fun onFailure(call: Call<ParticipatedContests>, t: Throwable) {
                participatedContests.postValue(Resource.error(t.message,null))
            }
        })
    }


    fun getContests(): LiveData<Resource<Contests>> {
        return contests
    }

    fun getParticipatedContests(): LiveData<Resource<ParticipatedContests>> {
        return participatedContests
    }

}