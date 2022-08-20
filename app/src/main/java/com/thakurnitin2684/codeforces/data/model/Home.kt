package com.thakurnitin2684.codeforces.data.model


import com.google.gson.annotations.SerializedName

data class Home (

    @SerializedName("status" )
    var status : String?= null,
    @SerializedName("result" )
    var result : ArrayList<Result> = arrayListOf()

)

data class Result(

    @SerializedName("lastName")
    var lastName: String? = "--",
    @SerializedName("country")
    var country: String? = "--",
    @SerializedName("lastOnlineTimeSeconds")
    var lastOnlineTimeSeconds: Int =0,
    @SerializedName("city")
    var city: String? = "--",
    @SerializedName("rating")
    var rating: Int? = 0,
    @SerializedName("friendOfCount")
    var friendOfCount: Int? = 0,
    @SerializedName("titlePhoto")
    var titlePhoto: String? = "--",
    @SerializedName("handle")
    var handle: String? = "--",
    @SerializedName("avatar")
    var avatar: String? = "--",
    @SerializedName("firstName")
    var firstName: String? = "--",
    @SerializedName("contribution")
    var contribution: Int? = 0,
    @SerializedName("organization")
    var organization: String? = "--",
    @SerializedName("rank")
    var rank: String? = "--",
    @SerializedName("maxRating")
    var maxRating: Int? = 0,
    @SerializedName("registrationTimeSeconds")
    var registrationTimeSeconds: Int=0,
    @SerializedName("maxRank")
    var maxRank: String? = "--"

)