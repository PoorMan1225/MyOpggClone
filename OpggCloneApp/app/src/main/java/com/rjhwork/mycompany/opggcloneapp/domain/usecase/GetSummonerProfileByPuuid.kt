package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile
import com.rjhwork.mycompany.opggcloneapp.data.repository.SummonerProfileRepo
import kotlinx.coroutines.coroutineScope

class GetSummonerProfileByPuuid(
    private val summonerProfileRepo: SummonerProfileRepo
) {

    suspend operator fun invoke(puuid:String): SummonerProfile {
        val response = summonerProfileRepo.getRequestSummonerPuuidProfile(puuid)
        return if (response.isSuccessful.not() || response.body() == null) {
            SummonerProfile()
        } else {
            response.body()!!
        }
    }
}