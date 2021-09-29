package com.rjhwork.mycompany.opggcloneapp.data.entity.match


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("dataVersion")
    val dataVersion: String?,
    @SerializedName("matchId")
    val matchId: String?,
    @SerializedName("participants")
    val participants: List<String>?
)