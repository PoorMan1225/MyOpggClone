package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.api.RiotApi
import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class RankingDataRepoImpl(
    private val dispatchers: CoroutineDispatcher,
    private val riotApi: RiotApi
): RankingDataRepo {

    override suspend fun getChallengerRankingData(): List<RankingEntity>? = withContext(dispatchers) {
        riotApi.getChallengerRankingData().body()
    }

    override suspend fun getGrandMasterRankingData(): List<RankingEntity>? = withContext(dispatchers) {
        riotApi.getGrandMasterRankingData().body()
    }

    override suspend fun getMasterRankingData(): List<RankingEntity>? = withContext(dispatchers) {
        riotApi.getMasterRankingData().body()
    }
}