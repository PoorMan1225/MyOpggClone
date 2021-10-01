package com.rjhwork.mycompany.opggcloneapp.presentation.search

import android.content.Context
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

    override fun onViewCreated() {
        fetchProfileSummonerData(true)
        fetchFavoriteData()
    }

    private fun fetchFavoriteData() = scope.launch {
        try {
            view.showFavoriteLoadingIndicator()
            val summonerName = preferenceManager.getSummonerProfile(SUMMONER_PROFILE_KEY)?.name
            lateinit var favoriteList: List<FavoriteEntity>
            view.showEmptyFavoriteLayout()

            summonerName?.let { name ->
                getFavoriteFilterItems(name).run {
                    favoriteList = this
                    if (size > 0)
                        view.showFavoriteDataLayout()
                }
            } ?: kotlin.run {
                favoriteList = getAllFavoriteData()
            }
            view.showFavoriteDataList(favoriteList)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            view.dismissFavoriteLoadingIndicator()
        }
    }

    override fun retry(dataCheck: Boolean) {
        fetchProfileSummonerData(dataCheck)
    }

    override fun getFavoriteBySummonerName(summonerName: String) {
        scope.launch {
            try {
                val favoriteEntity = getFavoriteByName(summonerName)
                view.startMatchActivityWithAnimation(favoriteEntity, null)
            } catch (e: Exception) {
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
                    summonerProfile?.let { profile ->
                        // 데이터가 변경되었을 수도 있기 때문에 데이터를 다시 가져온다.
                        val profileData = getSummonerProfileByPuuid(profile.puuid!!)
                        val profileMostChampionModel =
                            getSummonerMatchMostData(profile.puuid)
                        val profileIcon =
                            DataDragonApi.getSummonerProfileIcon(profileData.profileIconId.toString())
                        val profileLeagueItem = getSummonerProfileLeagueData(profileData.id!!)
                        val profileLevel = profileData.summonerLevel
                        val profileName = profileData.name
                        val profileRank = getProfileRank(
                            profileLeagueItem,
                            (view as SearchFragment).requireContext()
                        )

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

    private fun getProfileRank(
        profileLeagueItem: List<ProfileLeagueItem?>?,
        context: Context
    ): ProfileRank {
        profileLeagueItem?.let { list ->
            return when {
                list.size > 1 -> {
                    val sortedList = list.sortedByDescending { it?.queueType == "RANKED_SOLO_5x5" }
                    ProfileRank(
                        sortedList[0]?.tier?.toRankDrawable(context),
                        toTierToLetter(sortedList[0]?.rank, sortedList[0]?.tier),
                        "${sortedList[0]?.leaguePoints}LP"
                    )
                }
                list[0]?.queueType == "RANKED_SOLO_5x5" -> {
                    ProfileRank(
                        list[0]?.tier?.toRankDrawable(context),
                        toTierToLetter(list[0]?.rank, list[0]?.tier),
                        "${list[0]?.leaguePoints}LP"
                    )
                }
                else -> ProfileRank(null, null, null)
            }
        } ?: kotlin.run {
            return ProfileRank(null, null, null)
        }
    }
}