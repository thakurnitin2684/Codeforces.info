package com.thakurnitin2684.codeforces.data.repository

import com.thakurnitin2684.codeforces.data.api.ApiHelper
import javax.inject.Inject


class ParticipationRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getRecentSubmissions(handle: String)=  apiHelper.getRecentSubmissions(handle)


}
