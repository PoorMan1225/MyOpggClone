package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("bans")
    val bans: List<Ban>?,
    @SerializedName("objectives")
    val objectives: Objectives?,
    @SerializedName("teamId")
    val teamId: Int?,
    @SerializedName("win")
    val win: Boolean?
)