package com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata


import com.google.gson.annotations.SerializedName

data class ProfileLeagueItem(
    @SerializedName("freshBlood")
    val freshBlood: Boolean? = null,
    @SerializedName("hotStreak")
    val hotStreak: Boolean? = null,
    @SerializedName("inactive")
    val inactive: Boolean? = null,
    @SerializedName("leagueId")
    val leagueId: String? = null,
    @SerializedName("leaguePoints")
    val leaguePoints: Int? = null,
    @SerializedName("losses")
    val losses: Int? = null,
    @SerializedName("queueType")
    val queueType: String? = null,
    @SerializedName("rank")
    val rank: String? = null,
    @SerializedName("summonerId")
    val summonerId: String? = null,
    @SerializedName("summonerName")
    val summonerName: String? = null,
    @SerializedName("tier")
    val tier: String? = null,
    @SerializedName("veteran")
    val veteran: Boolean? = null,
    @SerializedName("wins")
    val wins: Int? = null
)