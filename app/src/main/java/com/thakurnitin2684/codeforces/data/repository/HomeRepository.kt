package com.thakurnitin2684.codeforces.data.repository

import com.thakurnitin2684.codeforces.data.api.ApiHelper
import javax.inject.Inject


class HomeRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun getHomeInfo(handle: String)=  apiHelper.getHomeInfo(handle)


}