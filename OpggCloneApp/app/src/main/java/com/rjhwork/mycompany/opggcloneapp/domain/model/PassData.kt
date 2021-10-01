package com.rjhwork.mycompany.opggcloneapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassData(
    val matchId:String,
    val summonerPuuid: String,
    val winLoseFlag: Boolean
): Parcelable {
}