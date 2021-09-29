package com.rjhwork.mycompany.opggcloneapp.data.mapper

import com.rjhwork.mycompany.opggcloneapp.data.entity.spell.Spell

fun Spell.getSpellIdByKey(key:String): String? {

    return with(data) {
        when(key) {
            summonerBarrier.key -> summonerBarrier.id
            summonerBoost.key -> summonerBoost.id
            summonerDot.key -> summonerDot.id
            summonerExhaust.key -> summonerExhaust.id
            summonerFlash.key -> summonerFlash.id
            summonerHaste.key -> summonerHaste.id
            summonerHeal.key -> summonerHeal.id
            summonerMana.key -> summonerMana.id
            summonerPoroRecall.key -> summonerPoroRecall.id
            summonerSmite.key -> summonerSmite.id
            summonerPoroThrow.key -> summonerPoroThrow.id
            summonerSnowball.key -> summonerSnowball.id
            summonerTeleport.key -> summonerTeleport.id
            summoner_UltBook_Placeholder.key -> summoner_UltBook_Placeholder.id
            else -> null
        }
    }
}