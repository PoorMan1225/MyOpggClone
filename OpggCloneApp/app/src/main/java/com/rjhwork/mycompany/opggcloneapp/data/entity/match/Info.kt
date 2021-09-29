package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("gameCreation")
    val gameCreation: Long? = null,
    @SerializedName("gameDuration")
    val gameDuration: Int? = null,
    @SerializedName("gameId")
    val gameId: Long? = null,
    @SerializedName("gameMode")
    val gameMode: String? = null,
    @SerializedName("gameName")
    val gameName: String? = null,
    @SerializedName("gameStartTimestamp")
    val gameStartTimestamp: Long? = null,
    @SerializedName("gameType")
    val gameType: String? = null,
    @SerializedName("gameVersion")
    val gameVersion: String? = null,
    @SerializedName("mapId")
    val mapId: Int? = null,
    @SerializedName("participants")
    val participants: List<Participant>? = null,
    @SerializedName("platformId")
    val platformId: String? = null,
    @SerializedName("queueId")
    val queueId: Int? = null,
    @SerializedName("teams")
    val teams: List<Team>? = null,
    @SerializedName("tournamentCode")
    val tournamentCode: String? = null
)