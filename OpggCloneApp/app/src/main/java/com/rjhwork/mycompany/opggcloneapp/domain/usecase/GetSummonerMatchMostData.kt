package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileChampionModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileMostChampionModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.RecentAllKdaModel
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GetSummonerMatchMostData(
    private val getSummonerMatchData: GetSummonerMatchData,
    private val getSummonerMatchesList: GetSummonerMatchesList
) {

    suspend operator fun invoke(puuid: String): ProfileMostChampionModel? =
        withContext(Dispatchers.Default) {
            val matchDataList = mutableListOf<ProfileChampionModel>()

            val matchIds = getSummonerMatchesList.invoke(puuid, 0)
            matchIds?.forEach { matchId ->
                val model = getSummonerMatchData.invoke(matchId)
                if (model.championName.isNullOrBlank().not()) {
                    val index = matchDataList.containsModel(model)
                    if (index > -1) {
                        val oldModel = matchDataList[index]
                        val newModel = oldModel.apply {
                            count += 1
                            if (model.winFlag) {
                                winCount = 1
                            } else {
                                winCount = 0
                            }
                            kill = model.kill
                            death = model.death
                            assist = model.assist
                        }
                        matchDataList[index] = newModel
                    } else {
                        model.championName?.let { name ->
                            model.championIcon = DataDragonApi.getChampionIconUrl(name)
                        }
                        matchDataList.add(model)
                    }
                }
            }
            val sortedList = matchDataList.sortedWith(ProfileChampionModel.CountComparator)
            getProfileMostChampionModel(sortedList.toMutableList())
        }

    private fun getProfileMostChampionModel(matchDataList: MutableList<ProfileChampionModel>) =
        when {
            matchDataList.size >= 3 -> ProfileMostChampionModel(
                matchDataList[0],
                matchDataList[1],
                matchDataList[2]
            )
            matchDataList.size == 2 -> ProfileMostChampionModel(
                matchDataList[0],
                matchDataList[1]
            )
            matchDataList.size == 1 -> ProfileMostChampionModel(
                matchDataList[0]
            )
            else -> null
        }

    private fun <T> Collection<T>.containsModel(value: Any): Int {
        this.forEachIndexed { index, t ->
            if (t!! == value)
                return index
        }
        return -1
    }
}

