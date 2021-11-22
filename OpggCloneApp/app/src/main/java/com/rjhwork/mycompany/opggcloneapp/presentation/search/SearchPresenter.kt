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
        // 최초 앱실행시 코루틴 Flow 에서 방출된 데이터를 가지고
        // Ui를 업데이트 한다.
        getTrackingFavoriteData()
            .onEach { fetchFavoriteData() }
            .launchIn(scope) // 어느 스레드에서 흐를 것인지 결정.
    }

    override fun onViewCreated() {
        fetchProfileSummonerData(true)
        fetchFavoriteData()
    }

    // Repository 에서 가져온 APi 데이터를 io 스레드에서 가져온후에
    // Ui를 업데이트 시키는 역할을 한다.
    private fun fetchFavoriteData() = scope.launch {
        try {
            // 로딩 다이얼 로그를 보여주는 역할.
            view.showFavoriteLoadingIndicator()
            val summonerName = preferenceManager.getSummonerProfile(SUMMONER_PROFILE_KEY)?.name
            lateinit var favoriteList: List<FavoriteEntity>
            view.showEmptyFavoriteLayout()

            // 기존에 카드뷰의 즐겨찾기 데이터가 존재하는 경우 그 데이터는 제외하고
            // 나머지 즐겨찾기 데이터만 가져와서 리사이클러뷰에 보여준다.
            summonerName?.let { name ->
                getFavoriteFilterItems(name).run {
                    favoriteList = this
                    if (size > 0)
                        // recyclerView 의 visibility 를 true 로 변경해준다.
                        view.showFavoriteDataLayout()
                }
            } ?: kotlin.run {
                favoriteList = getAllFavoriteData()
            }
            view.showFavoriteDataList(favoriteList)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // 로딩 다이얼로그는 항상 꺼준다.
            view.dismissFavoriteLoadingIndicator()
        }
    }

    // 프래그먼트에서 화면 전환시에 데이터값이 변경이 되어 있을 수 있기
    // 때문에 새로 호출해서 재갱신된 데이터를 보여주게 된다.
    override fun retry(dataCheck: Boolean) {
        // 카드뷰에 icon, 랭킹정보 등을 가져오는 함수.
        fetchProfileSummonerData(dataCheck)
    }

    // View 쪽에서 데이터를 가지고 다른 프래그먼트로 가게 되었을 때
    // 해당 엔티티를 넘겨주고 애니메이션과 함께 전환이 이루어지게 된다.
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

    // 카드뷰에 이미상의 icon 이나 티어정보, 등등을 가져오는 함수 이다.
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