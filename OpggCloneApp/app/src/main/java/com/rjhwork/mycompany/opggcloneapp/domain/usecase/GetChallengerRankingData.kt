package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.repository.RankingDataRepo

class GetChallengerRankingData(
    private var rankingDataRepo: RankingDataRepo
) {
    suspend operator fun invoke() = rankingDataRepo.getChallengerRankingData()
}