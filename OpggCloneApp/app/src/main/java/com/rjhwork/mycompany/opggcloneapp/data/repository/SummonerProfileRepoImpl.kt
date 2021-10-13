package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.api.RiotApi
import com.rjhwork.mycompany.opggcloneapp.data.api.RiotMatchApi
import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class SummonerProfileRepoImpl(
    private val riotApi: RiotApi,
    private val riotMatchApi: RiotMatchApi,
    private val dispatchers: CoroutineDispatcher
) : SummonerProfileRepo {

    override suspend fun getRequestSummonerNameProfile(summonerName: String): Response<SummonerProfile> =
        withContext(dispatchers) {
            riotApi.getRequestSummonerNameProfile(summonerName)
        }

    override suspend fun getRequestSummonerPuuidProfile(puuid: String): Response<SummonerProfile> =
        withContext(dispatchers) {
            riotApi.getRequestSummonerPuuidProfile(puuid)
        }

    override suspend fun getRequestMatchesList(puuid: String, start: Int): Response<List<String>> =
        withContext(dispatchers) {
            riotMatchApi.getRequestSummonerMatches(puuid, start = start)
        }

    override suspend fun getRequestMatchByMatchId(matchId: String): Response<Match> =
        withContext(dispatchers) {
            riotMatchApi.getRequestSummonerMatchByMatchId(matchId)
        }

    override suspend fun getRequestSummonerLeagueProfile(summonerId: String): Response<List<ProfileLeagueItem>> =
        withContext(dispatchers) {
            riotApi.getRequestSummonerLeagueProfile(summonerId)
        }
}