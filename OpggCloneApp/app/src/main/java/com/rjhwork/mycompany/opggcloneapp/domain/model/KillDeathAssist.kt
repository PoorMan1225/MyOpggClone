package com.rjhwork.mycompany.opggcloneapp.domain.model

data class KillDeathAssist(
    val killAssistRate: Int,
    val kill: Int?,
    val death: Int?,
    val assist: Int?,
    val pentaKills:Int?,
    val quadraKills:Int?,
    val tripleKills:Int?,
    val doubleKills:Int?,
) {
}