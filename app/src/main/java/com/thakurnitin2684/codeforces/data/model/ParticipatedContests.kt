package com.thakurnitin2684.codeforces.data.model

import com.google.gson.annotations.SerializedName


data class ParticipatedContests(

    @SerializedName("status") var status: String? = null,
    @SerializedName("result") var result: ArrayList<ParticipatedContest> = arrayListOf()

)


data class ParticipatedContest(

    @SerializedName("contestId") var contestId: Int = 0,
    @SerializedName("contestName") var contestName: String = "--",
    @SerializedName("handle") var handle: String = "--",
    @SerializedName("rank") var rank: Int = 0,
    @SerializedName("ratingUpdateTimeSeconds") var ratingUpdateTimeSeconds: Int = 0,
    @SerializedName("oldRating") var oldRating: Int = 0,
    @SerializedName("newRating") var newRating: Int = 0

)