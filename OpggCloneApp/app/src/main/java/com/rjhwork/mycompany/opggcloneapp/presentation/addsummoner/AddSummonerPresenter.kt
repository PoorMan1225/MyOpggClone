package com.rjhwork.mycompany.opggcloneapp.presentation.addsummoner

import com.rjhwork.mycompany.opggcloneapp.data.entity.SummonerProfile
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteRankEntity
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toTierToLetter
import com.rjhwork.mycompany.opggcloneapp.data.preference.PreferenceManager
import com.rjhwork.mycompany.opggcloneapp.data.preference.SharedPreferenceManager.Companion.SUMMONER_PROFILE_KEY
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi
import com.rjhwork.mycompany.opggcloneapp.util.Flag
import kotlinx.coroutines.*

class AddSummonerPresenter(
    private val view: AddSummonerContract.View,
    private val getSummonerProfileByName: GetSummonerProfileByName,
    private val preferenceManager: PreferenceManager,
    private val getSummonerProfileLeagueData: GetSummonerProfileLeagueData,
    private val insertFavoriteData: InsertFavoriteData,
    private val deleteFavoriteData: DeleteFavoriteData,
    private val getAllFavoriteData: GetAllFavoriteData
) : AddSummonerContract.Presenter {

    override var scope: CoroutineScope = MainScope()

    override fun fetchSummonerProfileData(name: String) {
        scope.launch {
            try {
                view.showDialog()
                val summonerProfile = getSummonerProfileByName(name)
                if (summonerProfile == null) {
                    view.showDialog(name)
                } else {
                    preferenceManager.putSummonerProfile(SUMMONER_PROFILE_KEY, summonerProfile)
                    val profileIcon = DataDragonApi.getSummonerProfileIcon(summonerProfile.profileIconId.toString())
                    val profileLeagueItem = getSummonerProfileLeagueData(summonerProfile.id!!)

                    insertFavoriteDataToDB(profileIcon, summonerProfile, profileLeagueItem?.first())

                    Flag.dataCheck = true
                    view.finishActivityAnimation()
                }
            } catch (e: Exception) {
                view.showToast("데이터를 가져오는데 실패했습니다.")
                e.printStackTrace()
            } finally {
                view.dismissDialog()
            }
        }
    }

    private suspend fun insertFavoriteDataToDB(
        profileIcon: String?,
        summonerProfile: SummonerProfile,
        profileLeagueItem: ProfileLeagueItem?
    ) = coroutineScope {
        summonerProfile.name?.let {
            val dataList = getAllFavoriteData()
            if (dataList.isNullOrEmpty().not() && dataList.size >= 10) {
                val lastData = dataList.last()
                deleteFavoriteData(lastData)
            }

            insertFavoriteData.invoke(
                FavoriteEntity(
                    summonerName = summonerProfile.name,
                    summonerIcon = profileIcon,
                    profileRank = FavoriteRankEntity(
                        tier = toTierToLetter(profileLeagueItem?.rank, profileLeagueItem?.tier),
                        rank = profileLeagueItem?.tier
                    ),
                    summonerLevel = summonerProfile.summonerLevel?.toString(),
                    summonerPuuid = summonerProfile.puuid,
                    isFavorite = null
                )
            )
        }
    }

    override fun onCreate() {}
}