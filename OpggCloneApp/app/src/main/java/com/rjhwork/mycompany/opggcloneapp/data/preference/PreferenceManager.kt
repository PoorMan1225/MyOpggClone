package com.rjhwork.mycompany.opggcloneapp.data.preference

import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile

interface PreferenceManager {

    fun getString(key:String): String?

    fun putString(key:String, value:String)

    fun putSummonerProfile(key:String, value:SummonerProfile?)

    fun getSummonerProfile(key:String): SummonerProfile?

    fun getLong(key: String): Long

    fun putLong(key: String, value: Long)
}