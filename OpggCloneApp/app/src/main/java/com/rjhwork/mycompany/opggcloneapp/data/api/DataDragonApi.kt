package com.rjhwork.mycompany.opggcloneapp.data.api

import com.rjhwork.mycompany.opggcloneapp.BuildConfig
import com.rjhwork.mycompany.opggcloneapp.data.entity.rune.DataItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.spell.Spell
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DataDragonApi {

    @GET("cdn/${BuildConfig.VERSION}/data/en_US/summoner.json")
    suspend fun getRequestSummonerSpells(): Response<Spell>

    @GET("cdn/${BuildConfig.VERSION}/data/en_US/runesReforged.json")
    suspend fun getRequestSummonerRuns(): Response<List<DataItem>>
}