package com.rjhwork.mycompany.opggcloneapp.domain.usecase


import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.repository.SummonerProfileRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class GetSummonerProfileLeagueData(
    private val summonerProfileRepo: SummonerProfileRepo
) {
    suspend operator fun invoke(summonerId: String): List<ProfileLeagueItem?>? {
        val response = summonerProfileRepo.getRequestSummonerLeagueProfile(summonerId)
        return response.body()
    }
}