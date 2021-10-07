package com.rjhwork.mycompany.opggcloneapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BindAnalysisData(
    val puuid:String?,
    val win:Boolean?,
    val kills:Int?,
    val championName:String?,
    val goldEarned: Int?,
    val totalDamageDealtToChampions: Int?,
    val totalMinionsKilled: Int?,
    val totalDamageShieldedOnTeammates: Int?,
    val wardsPlaced:Int?
): Parcelable {
}