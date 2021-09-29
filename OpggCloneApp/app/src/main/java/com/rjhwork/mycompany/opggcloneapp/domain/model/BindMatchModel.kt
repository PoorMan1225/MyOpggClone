package com.rjhwork.mycompany.opggcloneapp.domain.model

data class BindMatchModel(
    val puuid: String,
    val matchId: String,
    val win:Boolean?,
    val date:String,
    val gameMode:String?,
    val matchDuration:String,
    val championIcon: String?,
    val spell1Icon: String?,
    val spell2Icon: String?,
    val rune1Icon: String?,
    val rune2Icon: String?,
    val killDeathAssist: KillDeathAssist,
    val time:Long?,
    val items: Items
    ) {
}