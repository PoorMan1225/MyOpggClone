package com.rjhwork.mycompany.opggcloneapp.util

import com.rjhwork.mycompany.opggcloneapp.BuildConfig
import com.rjhwork.mycompany.opggcloneapp.data.mapper.checkOrnnItem

object DataDragonApi {
    fun getChampionIconUrl(name:String): String {
        val correctName = if(name == "FiddleSticks") {
            "Fiddlesticks"
        } else {
            name
        }
        return "https://ddragon.leagueoflegends.com/cdn/${BuildConfig.VERSION}/img/champion/${correctName}.png"
    }

    fun getSummonerProfileIcon(iconId:String) : String {
        return "https://ddragon.leagueoflegends.com/cdn/${BuildConfig.VERSION}/img/profileicon/${iconId}.png"
    }

    fun getRequestItemImageUrl(itemId: String): String? {
        if(itemId == "0") return null
        if(checkOrnnItem(itemId)) return itemId
        return "https://ddragon.leagueoflegends.com/cdn/${BuildConfig.VERSION}/img/item/${itemId}.png"
    }

    fun getSummonerSpellIconUrl(spellId: String): String {
        return "https://ddragon.leagueoflegends.com/cdn/${BuildConfig.VERSION}/img/spell/${spellId}.png"
    }

    fun getSummonerRuneImageUrl(iconImageUrl:String): String {
        return "https://ddragon.leagueoflegends.com/cdn/img/${iconImageUrl}"
    }
}