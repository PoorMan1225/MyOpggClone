package com.rjhwork.mycompany.opggcloneapp.presentation.favorite

import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteRankEntity
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toTierToLetter
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoritePresenter(
    private val view: FavoriteContract.View,
    private val getTrackingFavoriteData: GetTrackingFavoriteData,
    private val getAllFavoriteData: GetAllFavoriteData,
    private val updateFavoriteData: UpdateFavoriteData,
    private val deleteFavoriteData: DeleteFavoriteData,
    private val insertFavoriteData: InsertFavoriteData,
    private val getSummonerProfileByName: GetSummonerProfileByName,
    private val getSummonerProfileLeagueData: GetSummonerProfileLeagueData

) : FavoriteContract.Presenter {

    override val trackingFavoriteItem: List<FavoriteEntity> = emptyList()

    override var scope: CoroutineScope = MainScope()

    init {
        getTrackingFavoriteData()
            .onEach { fetchFavoriteItems() }
            .launchIn(scope)
    }

    override fun onCreate() {
        fetchFavoriteItems()
    }

    override fun updateFavoriteData(favoriteEntity: FavoriteEntity) {
        scope.launch {
            updateFavoriteData.invoke(favoriteEntity)
        }
    }

    override fun deleteFavoriteData(favoriteEntity: FavoriteEntity) {
        scope.launch {
            deleteFavoriteData.invoke(favoriteEntity)
        }
    }

    override fun requestFavoriteData(summonerName: String) {
        scope.launch {
            try {
                view.showLoadingIndicator()
                val summonerProfile = getSummonerProfileByName(summonerName)
                if (summonerProfile == null) {
                    view.showDialog(summonerName)
                } else {
                    val profileIcon = DataDragonApi.getSummonerProfileIcon(summonerProfile.profileIconId.toString())
                    val profileLeagueItem = getSummonerProfileLeagueData(summonerProfile.id!!)
                    insertFavoriteDataToDB(profileIcon, summonerProfile, profileLeagueItem?.first())
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                view.dismissLoadingIndicator()
            }
        }
    }

    private suspend fun insertFavoriteDataToDB(
        profileIcon: String?,
        summonerProfile: SummonerProfile,
        profileLeagueItem: ProfileLeagueItem?
    ) {
        summonerProfile.name?.let {
            val dataList = getAllFavoriteData()
            if (dataList.isNullOrEmpty().not() && dataList.size >= 10) {
                val lastData = dataList.last()
                deleteFavoriteData(lastData)
            }

            val favoriteEntity = FavoriteEntity(
                summonerName = summonerProfile.name,
                summonerIcon = profileIcon,
                profileRank = FavoriteRankEntity(
                    tier = toTierToLetter(profileLeagueItem?.rank, profileLeagueItem?.tier),
                    rank = profileLeagueItem?.tier
                ),
                summonerLevel = summonerProfile.summonerLevel?.toString(),
                summonerPuuid = summonerProfile.puuid
            )
            insertFavoriteData.invoke(favoriteEntity)
            view.startToMatchListActivityWithAnimation(favoriteEntity)
        }
    }

    private fun fetchFavoriteItems() = scope.launch {
        try {
            val dataList = getAllFavoriteData()
            if (dataList.isNullOrEmpty()) {
                view.showNoDataDescription()
            } else {
                view.showTrackingFavoriteData(dataList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}