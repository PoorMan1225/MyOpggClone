package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingEntity

interface RankingDataRepo {

    suspend fun getChallengerRankingData(): List<RankingEntity>?

    suspend fun getGrandMasterRankingData(): List<RankingEntity>?

    suspend fun getMasterRankingData(): List<RankingEntity>?
}