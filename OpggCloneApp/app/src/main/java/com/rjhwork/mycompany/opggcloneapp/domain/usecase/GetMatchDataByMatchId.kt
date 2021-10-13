package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import android.util.Log
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.data.repository.SummonerProfileRepo

class GetMatchDataByMatchId(
    private val summonerProfileRepo: SummonerProfileRepo
) {
    suspend operator fun invoke(matchId: String): Match?  {
        return summonerProfileRepo.getRequestMatchByMatchId(matchId).body()
    }
}