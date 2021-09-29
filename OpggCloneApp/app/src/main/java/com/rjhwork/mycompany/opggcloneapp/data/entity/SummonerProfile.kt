package com.rjhwork.mycompany.opggcloneapp.data.entity


import com.google.gson.annotations.SerializedName

data class SummonerProfile(
    @SerializedName("accountId")
    val accountId: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("profileIconId")
    val profileIconId: Int? = null,
    @SerializedName("puuid")
    val puuid: String? = null,
    @SerializedName("revisionDate")
    val revisionDate: Long? = null,
    @SerializedName("summonerLevel")
    val summonerLevel: Int? = null
)