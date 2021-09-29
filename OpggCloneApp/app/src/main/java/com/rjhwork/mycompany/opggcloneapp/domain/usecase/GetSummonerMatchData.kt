package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.mapper.toProfileChampionModel
import com.rjhwork.mycompany.opggcloneapp.data.preference.PreferenceManager
import com.rjhwork.mycompany.opggcloneapp.data.preference.SharedPreferenceManager.Companion.SUMMONER_PROFILE_KEY
import com.rjhwork.mycompany.opggcloneapp.data.repository.SummonerProfileRepo
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileChampionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GetSummonerMatchData(
    private val summonerProfileRepo: SummonerProfileRepo,
    private val preferenceManager: PreferenceManager
) {
    suspend operator fun invoke(matchId: String): ProfileChampionModel = withContext(Dispatchers.Default) {
        val response = summonerProfileRepo.getRequestMatchByMatchId(matchId)
        val match = response.body()

        val profileData = match?.info?.participants?.find { participant ->
            preferenceManager.getSummonerProfile(SUMMONER_PROFILE_KEY)?.let { summonerProfile ->
                participant.puuid == summonerProfile.puuid
            } ?: false
        }

        profileData?.toProfileChampionModel() ?: ProfileChampionModel(null)
    }
}