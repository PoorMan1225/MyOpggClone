package com.rjhwork.mycompany.opggcloneapp.data.mapper

import com.rjhwork.mycompany.opggcloneapp.R

fun checkOrnnItem(itemId: String): Boolean {
    return when (itemId.toInt()) {
        in 7000..7022 -> true
        else -> false
    }
}

fun getOrnnItemImage(itemId: String) =
    when (itemId) {
        "7000" -> R.drawable.sandshrikes_claw
        "7001" -> R.drawable.syzygy
        "7002" -> R.drawable.draktharrs_shadowcarver
        "7003" -> R.drawable.turbocharged_hexperiment
        "7004" -> R.drawable.forgefire_crest
        "7005" -> R.drawable.rimeforged_grasp
        "7006" -> R.drawable.typhoon
        "7007" -> R.drawable.wyrmfallen_sacrifice
        "7008" -> R.drawable.blood_ward
        "7009" -> R.drawable.icathias_curse
        "7010" -> R.drawable.vespertide
        "7011" -> R.drawable.upgraded_aeropack
        "7012" -> R.drawable.liandrys_lament
        "7013" -> R.drawable.eye_of_luden
        "7014" -> R.drawable.eternal_winter
        "7015" -> R.drawable.ceaseless_hunger
        "7016" -> R.drawable.dreamshatter
        "7017" -> R.drawable.decide
        "7018" -> R.drawable.infinity_force
        "7019" -> R.drawable.reliquary_of_the_golden_sister
        "7020" -> R.drawable.shurelayas_requiem
        "7021" -> R.drawable.star_caster
        "7022" -> R.drawable.seat_of_command
        else -> null
    }