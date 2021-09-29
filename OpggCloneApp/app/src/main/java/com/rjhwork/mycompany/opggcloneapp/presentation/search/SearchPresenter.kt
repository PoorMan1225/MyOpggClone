package com.rjhwork.mycompany.opggcloneapp.presentation.search

import android.util.Log
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toRankDrawable
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toTierToLetter
import com.rjhwork.mycompany.opggcloneapp.data.preference.PreferenceManager
import com.rjhwork.mycompany.opggcloneapp.data.preference.SharedPreferenceManager.Companion.SUMMONER_PROFILE_KEY
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindProfileModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileRank
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchPresenter(
    private val view: SearchContract.View,
    private val preferenceManager: PreferenceManager,
    private val getSummonerProfileByPuuid: GetSummonerProfileByPuuid,
    private val getSummonerMatchMostData: GetSummonerMatchMostData,
    private val getSummonerProfileLeagueData: GetSummonerProfileLeagueData,
    private val getFavoriteFilterItems: GetFavoriteFilterItems,
    getTrackingFavoriteData: GetTrackingFavoriteData,
    private val getAllFavoriteData: GetAllFavoriteData,
    private val getFavoriteByName: GetFavoriteByName
) : SearchContract.Presenter {

    override var scope: CoroutineScope = MainScope()

    init {
        getTrackingFavoriteData()
            .onEach {
                fetchFavoriteData()
            }
            .launchIn(scope)
    }

    override fun onCreateView() {
        fetchProfileSummonerData(true)
        fetchFavoriteData()
    }

    private fun fetchFavoriteData() = scope.launch {
        try{
            view.showFavoriteLoadingIndicator()
            val summonerName = preferenceManager.getSummonerProfile(SUMMONER_PROFILE_KEY)?.name
            lateinit var favoriteList: List<FavoriteEntity>
            view.showEmptyFavoriteLayout()

            summonerName?.let { summonerName ->
                getFavoriteFilterItems(summonerName).run {
                    favoriteList = this
                    if(size > 0)
                        view.showFavoriteDataLayout()
                }
            } ?: kotlin.run {
                favoriteList = getAllFavoriteData()
            }
            view.showFavoriteDataList(favoriteList)
        }catch (e: Exception) {
            e.printStackTrace()
        }finally {
            view.dismissFavoriteLoadingIndicator()
        }
    }

    override fun retry(dataCheck: Boolean) {
        fetchProfileSummonerData(dataCheck)
    }

    override fun getFavoriteBySummonerName(summonerName: String) {
        scope.launch {
            try{
                val favoriteEntity = getFavoriteByName(summonerName)
                view.startMatchActivityWithAnimation(favoriteEntity, null)
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {}

    private fun fetchProfileSummonerData(flag: Boolean) {
        if (flag) {
            view.touchEnabledFalse()
            view.showLoadingIndicator()

            scope.launch {
                try {
                    val summonerProfile = preferenceManager.getSummonerProfile(SUMMONER_PROFILE_KEY)
                    summonerProfile?.let { summonerProfile ->
                        // 데이터가 변경되었을 수도 있기 때문에 데이터를 다시 가져온다.
                        val profileData = getSummonerProfileByPuuid(summonerProfile.puuid!!)
                        val profileMostChampionModel = getSummonerMatchMostData(summonerProfile.puuid)
                        val profileIcon = DataDragonApi.getSummonerProfileIcon(profileData.profileIconId.toString())
                        val profileLeagueItem = getSummonerProfileLeagueData(profileData.id!!)
                        val profileLevel = profileData.summonerLevel
                        val profileName = profileData.name
                        val profileRank = getProfileRank(profileLeagueItem?.first())

                        view.showEmptyChampionDataLayout(profileMostChampionModel)
                        view.showEmptyRankLayout(profileRank)
                        view.showAddSummonerProfile(
                            BindProfileModel(
                                profileName,
                                profileIcon,
                                profileLevel,
                                profileMostChampionModel,
                                profileRank
                            )
                        )
                    } ?: run {
                        view.touchEnabledTrue()
                        view.deleteAddSummonerProfile()
                    }
                } catch (e: Exception) {
                    view.showToast("예상치못한 에러가 발생했습니다.")
                    view.touchEnabledTrue()
                    e.printStackTrace()
                } finally {
                    view.dismissLoadingIndicator()
                }
            }
        }
    }

    private fun getProfileRank(profileLeagueItem: ProfileLeagueItem?): ProfileRank {
        return if (profileLeagueItem == null) {
            ProfileRank(null, null, null)
        } else {
            ProfileRank(
                (view as SearchFragment).context?.let {
                    profileLeagueItem.tier?.toRankDrawable(it)
                },
                toTierToLetter(profileLeagueItem.rank, profileLeagueItem.tier),
                "${profileLeagueItem.leaguePoints?.toString()}LP"
            )
        }
    }
}