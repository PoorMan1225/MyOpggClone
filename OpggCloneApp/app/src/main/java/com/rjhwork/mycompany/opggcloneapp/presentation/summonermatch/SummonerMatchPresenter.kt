package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch

import android.util.Log
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.preference.PreferenceManager
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import kotlinx.coroutines.*

class SummonerMatchPresenter(
    private val view: SummonerMatchContract.View,
    private val getMatchDataDetail: GetMatchDataDetail,
    private val insertFavoriteData: InsertFavoriteData,
    private val getFavoriteByName: GetFavoriteByName,
    private val getSummonerMatchMostData: GetSummonerMatchMostData,
    private val getSummonerProfileByPuuid: GetSummonerProfileByPuuid,
    private val getSummonerProfileLeagueData: GetSummonerProfileLeagueData,
    private val preference: PreferenceManager
) : SummonerMatchContract.Presenter {

    override var scope: CoroutineScope = MainScope()

    override fun onCreate(favoriteEntity: FavoriteEntity?, value: String?) {
        favoriteEntity?.let {
            fetchDataList(it, value)
        }
    }

    override fun updateFavoriteData(favoriteEntity: FavoriteEntity) {
        scope.launch {
            try {
                val favorite = getData(favoriteEntity)
                view.setFavoriteImageView(
                    favorite.isFavorite?.let {
                        if (it) "true" else "false"
                    }
                )
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getData(favoriteEntity: FavoriteEntity): FavoriteEntity = coroutineScope {
        val defferedOne = async { insertFavoriteData.invoke(favoriteEntity) }
        defferedOne.await()
        getFavoriteByName.invoke(favoriteEntity.summonerName)
    }


    override fun showMoreList(favoriteEntity: FavoriteEntity, index: Int): Int =
        moreRequestDataList(favoriteEntity.summonerPuuid!!, index)

    override fun showRefreshList(favoriteEntity: FavoriteEntity) {
        val currentMills = System.currentTimeMillis()
        val getTimeMills = preference.getLong(favoriteEntity.summonerName)

        if (getTimeMills == Long.MIN_VALUE) {
            refreshProcess(favoriteEntity)
            return
        }

        if (currentMills >= (getTimeMills + (60 * 2 * 1000))) {
            refreshProcess(favoriteEntity)
        } else {
            view.showTimeLimitDialog(getTimeMills, currentMills)
        }
    }

    private fun refreshProcess(
        favoriteEntity: FavoriteEntity
    ) {
        scope.launch {
            favoriteEntity.summonerPuuid?.let { puuid ->
                try {
                    preference.putLong(favoriteEntity.summonerName, System.currentTimeMillis())
                    view.disableRefreshClick()
                    view.showRefreshProgress()
                    refreshData(puuid, favoriteEntity, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                    preference.putLong(favoriteEntity.summonerName, Long.MIN_VALUE)
                } finally {
                    view.dismissRefreshProgress()
                    view.enableRefreshClick()
                    view.dismissLoadingIndicator()
                }
            }
        }
    }

    override fun onCreate() {}

    private fun fetchDataList(favoriteEntity: FavoriteEntity, value: String?) = scope.launch {
        favoriteEntity.summonerPuuid?.let { puuid ->
            try {
                view.showLoadingIndicator()
                refreshData(puuid, favoriteEntity, value)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                view.dismissLoadingIndicator()
            }
        }
    }

    private fun moreRequestDataList(puuid: String, index: Int): Int {
        var checkException = 0

        scope.launch {
            try {
                val bindModelList = getMatchDataDetail.invoke(puuid, index)
                view.addListData(bindModelList)
            } catch (e: Exception) {
                e.printStackTrace()
                checkException = -1
            }
        }
        return checkException
    }

    private suspend fun refreshData(
        puuid: String,
        favoriteEntity: FavoriteEntity,
        value: String?
    ) {
        val summonerProfile = getSummonerProfileByPuuid.invoke(puuid)
        val summonerLeagueData =
            summonerProfile.id?.let { getSummonerProfileLeagueData.invoke(it) }
        val matchMostData = getSummonerMatchMostData.invoke(puuid)
        val bindModelList = getMatchDataDetail.invoke(puuid, 0)
        view.showListData(bindModelList, matchMostData)
        view.showProfile(favoriteEntity, value)
        view.showLeagueData(summonerLeagueData)
    }
}