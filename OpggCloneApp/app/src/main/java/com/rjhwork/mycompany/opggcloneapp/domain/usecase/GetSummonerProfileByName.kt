package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile
import com.rjhwork.mycompany.opggcloneapp.data.repository.SummonerProfileRepo
import kotlinx.coroutines.coroutineScope

class GetSummonerProfileByName(
    private val summonerProfileRepo: SummonerProfileRepo
) {
    suspend operator fun invoke(summonerName: String): SummonerProfile? {
        val response = summonerProfileRepo.getRequestSummonerNameProfile(summonerName)
        return response.body()
    }
}