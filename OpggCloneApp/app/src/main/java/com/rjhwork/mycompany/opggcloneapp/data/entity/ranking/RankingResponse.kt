package com.rjhwork.mycompany.opggcloneapp.data.entity.ranking

import com.google.gson.annotations.SerializedName

data class RankingResponse(
    @SerializedName("entries")
    val entries: List<RankingEntity>?,
    @SerializedName("leagueId")
    val leagueId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("queue")
    val queue: String?,
    @SerializedName("tier")
    val tier: String?
)