package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingResponse

interface RankingDataRepo {

    suspend fun getChallengerRankingData(): RankingResponse?

    suspend fun getGrandMasterRankingData(): RankingResponse?

    suspend fun getMasterRankingData(): RankingResponse?
}