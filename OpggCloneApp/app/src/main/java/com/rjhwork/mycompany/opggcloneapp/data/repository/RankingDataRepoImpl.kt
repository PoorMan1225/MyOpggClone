package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.api.RiotApi
import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RankingDataRepoImpl(
    private val dispatchers: CoroutineDispatcher,
    private val riotApi: RiotApi
): RankingDataRepo {

    override suspend fun getChallengerRankingData(): RankingResponse? = withContext(dispatchers) {
        riotApi.getChallengerRankingData().body()
    }

    override suspend fun getGrandMasterRankingData(): RankingResponse? = withContext(dispatchers) {
        riotApi.getGrandMasterRankingData().body()
    }

    override suspend fun getMasterRankingData(): RankingResponse? = withContext(dispatchers) {
        riotApi.getMasterRankingData().body()
    }
}