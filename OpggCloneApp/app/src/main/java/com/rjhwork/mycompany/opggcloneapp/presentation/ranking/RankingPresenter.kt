package com.rjhwork.mycompany.opggcloneapp.presentation.ranking

import android.util.Log
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingResponse
import com.rjhwork.mycompany.opggcloneapp.domain.model.RankingModel
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Comparator
import com.rjhwork.mycompany.opggcloneapp.util.*

class RankingPresenter(
    private var view: RankingContract.View,
    private val getChallengerRankingData: GetChallengerRankingData,
    private val getGrandMasterRankingData: GetGrandMasterRankingData,
    private val getMasterRankingData: GetMasterRankingData,
    private val getFavoriteByName: GetFavoriteByName,
    private val getSummonerProfileByName: GetSummonerProfileByName
) : RankingContract.Presenter {

    override var scope: CoroutineScope = MainScope()
    private var count = 1

    override fun onViewCreated() {
        fetchDataList()
    }

    private fun fetchDataList() {
        scope.launch {
            try {
                view.showLoadingIndicator()
                val list = getChallengerRankingData.invoke()
                list?.let { response ->
                    response
                        .run(::getRankingList)
                        .run(::getSortedList)
                        .run { view.fetchRankingData(this) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                view.dismissLoadingIndicator()
            }
        }
    }

    override fun showMoreRankingData() {
        scope.launch {
            when (count) {
                1 -> getDataProcess()
                2 -> getDataProcess()
                else -> view.noDataRankingList()
            }
        }
    }

    override fun getFavoriteBySummonerName(summonerName: String) {
        scope.launch {
            try {
                view.showLoadingIndicator()
                val favoriteEntity = getFavoriteByName.invoke(summonerName)
                view.startMatchActivityWithAnimation(
                    favoriteEntity,
                    favoriteEntity.isFavorite?.let {
                        if (it) "true" else "false"
                    })
            } catch (e: Exception) {
                Log.d("RankingPresenter", "${e.message}")
                makeNewFavoriteEntity(summonerName)
            } finally {
                view.dismissLoadingIndicator()
            }
        }
    }

    private suspend fun makeNewFavoriteEntity(summonerName: String) {
        try {
            val data = getSummonerProfileByName.invoke(summonerName)
            val favoriteEntity = FavoriteEntity(
                summonerName = data?.name ?: "Unknown",
                summonerPuuid = data?.puuid,
                summonerIcon = data?.profileIconId?.let {
                    DataDragonApi.getSummonerProfileIcon(it.toString())
                },
                summonerLevel = data?.summonerLevel.toString(),
                isFavorite = false
            )
            view.startMatchActivityWithAnimation(
                favoriteEntity,
                if (favoriteEntity.isFavorite!!) "true" else "false"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getSortedList(list: List<RankingModel>?): List<RankingModel>? {
        Log.d("RankingFragment", "getSortedList")
        return list?.sortedWith(RankingComparator)
    }

    private suspend fun getDataProcess() {
        try {
            count += 1
            val list = when (count) {
                2 -> getGrandMasterRankingData.invoke()
                3 -> getMasterRankingData.invoke()
                else -> return
            }
            list?.let { response ->
                response
                    .run(::getRankingList)
                    .run(::getSortedList)
                    .run { view.addRankingList(this) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            count -= 1
        }
    }

    private fun getRankingList(response: RankingResponse): List<RankingModel>? {
        val list = response.entries?.map { entity ->
            RankingModel(
                freshBlood = entity.freshBlood,
                hotStreak = entity.hotStreak,
                inactive = entity.inactive,
                leaguePoints = entity.leaguePoints,
                losses = entity.losses,
                rank = entity.rank,
                summonerName = entity.summonerName,
                summonerId = entity.summonerId,
                veteran = entity.veteran,
                wins = entity.wins,
                tier = response.tier,
                count = count
            )
        }
        return list
    }

    override fun onDestroyView() {}

    object RankingComparator : Comparator<RankingModel> {
        override fun compare(o1: RankingModel, o2: RankingModel): Int {
            val first = o1.leaguePoints
            val second = o2.leaguePoints
            return if (first != null && second != null)
                when {
                    first < second -> 1
                    else -> -1
                }
            else 0
        }
    }
}