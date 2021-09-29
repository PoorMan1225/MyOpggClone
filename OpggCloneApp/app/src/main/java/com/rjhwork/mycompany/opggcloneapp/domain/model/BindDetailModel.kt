package com.rjhwork.mycompany.opggcloneapp.domain.model

data class BindDetailModel(
    val maxDamage:Int,
    val championIcon:String?,
    val minuteCs: String,
    val spell1:String?,
    val spell2: String?,
    val rune1:String?,
    val rune2:String?,
    val tier: String,
    val summonerName:String,
    val kill:Int,
    val death:Int,
    val assist:Int,
    val kda: String,
    val items:Items,
    val cs:String,
    val earnedGold:String,
    val damage:Int
)