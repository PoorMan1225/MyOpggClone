package com.rjhwork.mycompany.opggcloneapp.domain.model

data class RankingModel(
    val freshBlood: Boolean?,
    val hotStreak: Boolean?,
    val inactive: Boolean?,
    val leaguePoints: Int?,
    val losses: Int?,
    val rank: String?,
    val summonerId: String?,
    val summonerName: String?,
    val veteran: Boolean?,
    val wins: Int?,
    val tier: String?,
    var count: Int,
) {
}