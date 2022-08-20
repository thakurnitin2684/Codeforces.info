package com.thakurnitin2684.codeforces.data.model

import com.google.gson.annotations.SerializedName


data class Contests(

    @SerializedName("status") var status: String? = null,
    @SerializedName("result") var result: ArrayList<Contest> = arrayListOf()

)

data class Contest(

    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "--",
    @SerializedName("type") var type: String = "--",
    @SerializedName("phase") var phase: String = "--",
    @SerializedName("frozen") var frozen: Boolean? = null,
    @SerializedName("durationSeconds") var durationSeconds: Int = 0,
    @SerializedName("startTimeSeconds") var startTimeSeconds: Int = 0,
    @SerializedName("relativeTimeSeconds") var relativeTimeSeconds: Int = 0

)