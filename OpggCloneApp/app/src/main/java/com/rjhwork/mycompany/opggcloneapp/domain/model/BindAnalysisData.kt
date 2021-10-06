package com.rjhwork.mycompany.opggcloneapp.domain.model

data class BindAnalysisData(
    val win:Boolean?,
    val kills:Int?,
    val championName:String?,
    val goldEarned: Int?,
    val totalDamageDealtToChampions: Int?,
    val totalMinionsKilled: Int?,
    val totalDamageShieldedOnTeammates: Int?,
    val wardsPlaced:Int?
) {
}