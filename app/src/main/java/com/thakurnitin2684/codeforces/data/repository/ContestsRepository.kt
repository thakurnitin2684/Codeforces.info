package com.thakurnitin2684.codeforces.data.repository

import com.thakurnitin2684.codeforces.data.api.ApiHelper
import javax.inject.Inject

    class ContestsRepository @Inject constructor(private val apiHelper: ApiHelper) {

        fun getContests()=  apiHelper.getContests()


        fun getParticipatedContests(handle: String) = apiHelper.getParticipatedContests(handle)
    }
