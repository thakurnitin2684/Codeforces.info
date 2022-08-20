package com.thakurnitin2684.codeforces.data.model
import com.google.gson.annotations.SerializedName


data class RecentSubmissions (

    @SerializedName("status" ) var status : String?           = null,
    @SerializedName("result" ) var result : ArrayList<RecentSubmission> = arrayListOf()

)

data class Problem (

    @SerializedName("contestId" ) var contestId : Int              = 0,
    @SerializedName("index"     ) var index     : String           = "--",
    @SerializedName("name"      ) var name      : String          = "--",
    @SerializedName("type"      ) var type      : String           = "--",
    @SerializedName("rating"    ) var rating    : Int              = 0,
    @SerializedName("tags"      ) var tags      : ArrayList<String> = arrayListOf()

)
data class Members (

    @SerializedName("handle" ) var handle : String = "--"

)

data class Author (

    @SerializedName("contestId"        ) var contestId        : Int               = 0,
    @SerializedName("members"          ) var members          : ArrayList<Members> = arrayListOf(),
    @SerializedName("participantType"  ) var participantType  : String            = "--",
    @SerializedName("ghost"            ) var ghost            : Boolean?           = null,
    @SerializedName("startTimeSeconds" ) var startTimeSeconds : Int               = 0

)

data class RecentSubmission (

    @SerializedName("id"                  ) var id                  : Int     = 0,
    @SerializedName("contestId"           ) var contestId           : Int     = 0,
    @SerializedName("creationTimeSeconds" ) var creationTimeSeconds : Int    = 0,
    @SerializedName("relativeTimeSeconds" ) var relativeTimeSeconds : Int     = 0,
    @SerializedName("problem"             ) var problem             : Problem = Problem(),
    @SerializedName("author"              ) var author              : Author  = Author(),
    @SerializedName("programmingLanguage" ) var programmingLanguage : String  = "--",
    @SerializedName("verdict"             ) var verdict             : String  = "--",
    @SerializedName("testset"             ) var testset             : String  = "--",
    @SerializedName("passedTestCount"     ) var passedTestCount     : Int    = 0,
    @SerializedName("timeConsumedMillis"  ) var timeConsumedMillis  : Int    = 0,
    @SerializedName("memoryConsumedBytes" ) var memoryConsumedBytes : Int     = 0

)