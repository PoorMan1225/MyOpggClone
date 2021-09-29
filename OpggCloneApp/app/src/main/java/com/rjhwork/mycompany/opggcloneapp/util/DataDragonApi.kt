package com.rjhwork.mycompany.opggcloneapp.util

import com.rjhwork.mycompany.opggcloneapp.BuildConfig

object DataDragonApi {
    fun getChampionIconUrl(name:String): String {
        return "https://ddragon.leagueoflegends.com/cdn/${BuildConfig.VERSION}/img/champion/${name}.png"
    }

    fun getSummonerProfileIcon(iconId:String) : String {
        return "https://ddragon.leagueoflegends.com/cdn/${BuildConfig.VERSION}/img/profileicon/${iconId}.png"
    }

    fun getRequestItemImageUrl(itemId: String): String? {
        if(itemId == "0") return null
        return "https://ddragon.leagueoflegends.com/cdn/${BuildConfig.VERSION}/img/item/${itemId}.png"
    }

    fun getSummonerSpellIconUrl(spellId: String): String {
        return "https://ddragon.leagueoflegends.com/cdn/${BuildConfig.VERSION}/img/spell/${spellId}.png"
    }

    fun getSummonerRuneImageUrl(iconImageUrl:String): String {
        return "https://ddragon.leagueoflegends.com/cdn/img/${iconImageUrl}"
    }
}