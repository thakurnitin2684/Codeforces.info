package com.thakurnitin2684.codeforces.data.api

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    fun getHomeInfo(handle: String) = apiService.getHomeInfo(handle)

    fun getContests() = apiService.getContests()

    fun getParticipatedContests(handle: String) = apiService.getParticipatedContests(handle)

    fun getRecentSubmissions(handle: String) = apiService.getRecentSubmissions(handle,"1","30")

}