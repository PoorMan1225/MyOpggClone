package com.rjhwork.mycompany.opggcloneapp.data.api

import com.rjhwork.mycompany.opggcloneapp.BuildConfig
import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RiotApi {

    @GET("lol/summoner/v4/summoners/by-name/{name}?api_key=${BuildConfig.RIOT_API_KEY}")
    suspend fun getRequestSummonerNameProfile(
        @Path("name") name:String
    ): Response<SummonerProfile>

    @GET("lol/summoner/v4/summoners/by-puuid/{puuid}?api_key=${BuildConfig.RIOT_API_KEY}")
    suspend fun getRequestSummonerPuuidProfile(
        @Path("puuid") puuid:String
    ): Response<SummonerProfile>

    @GET("lol/league/v4/entries/by-summoner/{summonerId}?api_key=${BuildConfig.RIOT_API_KEY}")
    suspend fun getRequestSummonerLeagueProfile(
        @Path("summonerId") summonerId: String
    ): Response<List<ProfileLeagueItem>>
}