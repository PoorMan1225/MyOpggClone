package com.rjhwork.mycompany.opggcloneapp.data.mapper

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.rjhwork.mycompany.opggcloneapp.R
import kotlin.math.roundToInt

fun String.toRankDrawable(context: Context): Drawable? {
    return when (this) {
        "IRON" -> ResourcesCompat.getDrawable(context.resources, R.drawable.iron, null)
        "BRONZE" -> ResourcesCompat.getDrawable(context.resources, R.drawable.bronze, null)
        "SILVER" -> ResourcesCompat.getDrawable(context.resources, R.drawable.silver, null)
        "GOLD" -> ResourcesCompat.getDrawable(context.resources, R.drawable.gold, null)
        "PLATINUM" -> ResourcesCompat.getDrawable(context.resources, R.drawable.platinum, null)
        "DIAMOND" -> ResourcesCompat.getDrawable(context.resources, R.drawable.diamond, null)
        "MASTER" -> ResourcesCompat.getDrawable(context.resources, R.drawable.master, null)
        "GRANDMASTER" -> ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.grandmaster,
            null
        )
        "CHALLENGER" -> ResourcesCompat.getDrawable(context.resources, R.drawable.challenger, null)
        else -> null
    }
}

fun toTierToLetter(rank: String?, tier: String?): String? {
    return when (rank) {
        "I" -> when (tier) {
            "CHALLENGER" -> tier
            "GRANDMASTER" -> tier
            "MASTER" -> tier
            else -> "$tier 1"
        }
        "II" -> "$tier 2"
        "III" -> "$tier 3"
        "IV" -> "$tier 4"
        else -> null
    }
}

fun String.toMMR() =
    when (this) {
        "IRON IV" -> 100
        "IRON III" -> 200
        "IRON II" -> 300
        "IRON I" -> 400
        "BRONZE IV" -> 500
        "BRONZE III" -> 600
        "BRONZE II" -> 700
        "BRONZE I" -> 800
        "SILVER IV" -> 900
        "SILVER III" -> 1000
        "SILVER II" -> 1100
        "SILVER I" -> 1200
        "GOLD IV" -> 1300
        "GOLD III" -> 1400
        "GOLD II" -> 1500
        "GOLD I" -> 1600
        "PLATINUM IV" -> 1700
        "PLATINUM III" -> 1800
        "PLATINUM II" -> 1900
        "PLATINUM I" -> 2000
        "DIAMOND IV" -> 2100
        "DIAMOND III" -> 2200
        "DIAMOND II" -> 2300
        "DIAMOND I" -> 2400
        "MASTER I" -> 2500
        "GRANDMASTER I" -> 2900
        "CHALLENGER I" -> 3300
        else -> 0
    }

fun Int.toRank() =
    when ((((this / 10) / 100f).roundToInt() * 100)) {
        0 -> "UNRANKED"
        in 1..100 -> "IRON 4"
        200 -> "IRON 3"
        300 -> "IRON 2"
        400 -> "IRON 1"
        500 -> "BRONZE 4"
        600 -> "BRONZE 3"
        700 -> "BRONZE 2"
        800 -> "BRONZE 1"
        900 -> "SILVER 4"
        1000 -> "SILVER 3"
        1100 -> "SILVER 2"
        1200 -> "SILVER 1"
        1300 -> "GOLD 4"
        1400 -> "GOLD 3"
        1500 -> "GOLD 2"
        1600 -> "GOLD 1"
        1700 -> "PLATINUM 4"
        1800 -> "PLATINUM 3"
        1900 -> "PLATINUM 2"
        2000 -> "PLATINUM 1"
        2100 -> "DIAMOND 4"
        2200 -> "DIAMOND 3"
        2300 -> "DIAMOND 2"
        2400 -> "DIAMOND 1"
        in 2500..2700 -> "MASTER"
        in 2700..3100 -> "GRANDMASTER"
        in 3100..3300 -> "CHALLENGER"
        else -> ""
    }

