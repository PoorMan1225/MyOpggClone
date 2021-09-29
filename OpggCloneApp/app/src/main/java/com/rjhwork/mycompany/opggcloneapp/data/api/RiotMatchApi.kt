package com.rjhwork.mycompany.opggcloneapp.data.api

import com.rjhwork.mycompany.opggcloneapp.BuildConfig
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RiotMatchApi {
    @GET("lol/match/v5/matches/by-puuid/{puuid}/ids?api_key=${BuildConfig.RIOT_API_KEY}")
    suspend fun getRequestSummonerMatches(
        @Path("puuid") puuid:String,
        @Query("start") start:Int = 0,
        @Query("count") count:Int = 10
    ): Response<List<String>>

    @GET("lol/match/v5/matches/{matchId}?api_key=${BuildConfig.RIOT_API_KEY}")
    suspend fun getRequestSummonerMatchByMatchId(
        @Path("matchId") matchId: String
    ): Response<Match>
}