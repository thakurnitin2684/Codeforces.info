package com.thakurnitin2684.codeforces.data.api

import com.thakurnitin2684.codeforces.data.model.Contests
import com.thakurnitin2684.codeforces.data.model.Home
import com.thakurnitin2684.codeforces.data.model.ParticipatedContests
import com.thakurnitin2684.codeforces.data.model.RecentSubmissions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("user.info?")
    fun getHomeInfo(@Query("handles") handle: String): Call<Home>

    @GET("contest.list")
    fun getContests(): Call<Contests>

    @GET("user.rating")
    fun getParticipatedContests(@Query("handle") handle: String): Call<ParticipatedContests>


    @GET("user.status")
    fun getRecentSubmissions(
        @Query("handle") handle: String,
        @Query("from") from: String,
        @Query("count") count: String,
    ): Call<RecentSubmissions>
}