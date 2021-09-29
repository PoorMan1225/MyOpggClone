package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.repository.SummonerProfileRepo
import kotlinx.coroutines.coroutineScope

class GetSummonerMatchesList(
    private val summonerProfileRepo: SummonerProfileRepo
) {
    suspend operator fun invoke(puuid: String, start:Int): List<String>? {
        val response = summonerProfileRepo.getRequestMatchesList(puuid, start)
        return response.body()
    }
}