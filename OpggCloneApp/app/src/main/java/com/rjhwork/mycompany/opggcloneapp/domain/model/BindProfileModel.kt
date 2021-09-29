package com.rjhwork.mycompany.opggcloneapp.domain.model

import android.graphics.Bitmap

data class BindProfileModel(
    val summonerName: String?,
    val summonerIcon: String?,
    val summonerLevel: Int?,
    val summonerMostChampionModel: ProfileMostChampionModel?,
    val profileRank: ProfileRank?
)