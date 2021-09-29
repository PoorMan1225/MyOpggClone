package com.rjhwork.mycompany.opggcloneapp.domain.model

data class BindDetailTotalModel(
    val winFlag:Boolean?,
    val totalKill:Int,
    val totalDeath:Int,
    val totalAssist:Int,
    val dragonKill:Int,
    val baronKill:Int,
    val towerKill:Int
) {
}