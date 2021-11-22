package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch

import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.preference.PreferenceManager
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import kotlinx.coroutines.*

// 사용자의 전적 리스트를 보여주는 역할을 하는 비즈니스 클래스
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

    // 즐겨찾기시 favorite 을 변경하기 위한 함수.
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

    // 즐겨찾기 버튼을 누르게 되면 데이터를 변경해주는 함수
    private suspend fun getData(favoriteEntity: FavoriteEntity): FavoriteEntity = coroutineScope {
        val defferedOne = async { insertFavoriteData.invoke(favoriteEntity) }
        // 먼저 데이터가 들어오고 변경된 데이터를 가져와야 하기때문에 await()을 하였다.
        defferedOne.await()
        getFavoriteByName.invoke(favoriteEntity.summonerName)
    }

    // 데이터를 추가적으로 요청하게 될경우에 인덱스 번호를 토대로 새로운 데이터를 가지고 오게된다.
    override fun showMoreList(favoriteEntity: FavoriteEntity, index: Int): Int =
        moreRequestDataList(favoriteEntity.summonerPuuid!!, index)

    // 전적 갱신 버튼을 누를시 갱신이 한번 되어있는 Entity 인지 확인해서
    // 갱신이 한번 된적이 있다면 120초 후에 다시 시도하게 만든다.
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

    // 리스트 데이터를 더 가져왔을때 데이터를 가져오지 못한 경우에는 index를 다시 빼주기 위해서
    // Exception -1 을 넘겨준다.
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