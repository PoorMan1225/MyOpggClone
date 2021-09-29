package com.rjhwork.mycompany.opggcloneapp.data.mapper

fun Int.gameMode(): String =
    when (this) {
        420 -> "솔랭"
        430 -> "일반"
        440 -> "무작위 총력전"
        450 -> "자유 5:5 랭크"
        else -> "Unknown"
    }