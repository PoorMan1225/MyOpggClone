package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import okhttp3.ResponseBody
import retrofit2.Response

interface SummonerProfileRepo {

    suspend fun getRequestSummonerNameProfile(summonerName:String): Response<SummonerProfile>

    suspend fun getRequestSummonerPuuidProfile(puuid:String): Response<SummonerProfile>

    suspend fun getRequestMatchesList(puuid:String, start:Int): Response<List<String>>

    suspend fun getRequestMatchByMatchId(matchId: String): Response<Match>

    suspend fun getRequestSummonerLeagueProfile(summonerId: String): Response<List<ProfileLeagueItem>>
}