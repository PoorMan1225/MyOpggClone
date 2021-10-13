package com.rjhwork.mycompany.opggcloneapp.data.api

import com.rjhwork.mycompany.opggcloneapp.BuildConfig
import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("lol/league-exp/v4/entries/RANKED_SOLO_5x5/{tier}/{division}")
    suspend fun getRankingData(
        @Path("tier") tier:String,
        @Path("division") division:String,
        @Query("page") page:Int,
    ): Response<List<RankingEntity>>

    @GET("lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=${BuildConfig.RIOT_API_KEY}")
    suspend fun getChallengerRankingData(): Response<List<RankingEntity>>

    @GET("lol/league/v4/grandmasterleagues/by-queue/RANKED_SOLO_5x5")
    suspend fun getGrandMasterRankingData(): Response<List<RankingEntity>>

    @GET("lol/league/v4/masterleagues/by-queue/RANKED_SOLO_5x5")
    suspend fun getMasterRankingData(): Response<List<RankingEntity>>
}