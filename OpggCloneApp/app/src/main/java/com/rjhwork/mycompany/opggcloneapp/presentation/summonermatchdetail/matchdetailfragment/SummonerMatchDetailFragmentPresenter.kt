package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchdetailfragment

import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Participant
import com.rjhwork.mycompany.opggcloneapp.data.entity.rune.DataItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.spell.Spell
import com.rjhwork.mycompany.opggcloneapp.data.mapper.getSpellIdByKey
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toMMR
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindDetailModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindDetailTotalModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.math.roundToInt

class SummonerMatchDetailFragmentPresenter(
    private val view: SummonerMatchDetailFragmentContract.View,
    private val getMatchDataByMatchId: GetMatchDataByMatchId,
    private val getSummonerProfileLeagueData: GetSummonerProfileLeagueData,
    private val getSummonerAllSpellData: GetSummonerAllSpellData,
    private val getRune1IconImageUrl: GetRune1IconImageUrl,
    private val getItemsData: GetItemsData,
    private val getRuneData: GetRuneData,
) : SummonerMatchDetailFragmentContract.Presenter {

    override var scope: CoroutineScope = MainScope()

    override fun onViewCreated() {}

    override fun onViewCreated(passData: PassData?) {
        fetchDataList(passData)
    }

    override fun onDestroyView() {}

    private fun fetchDataList(passData: PassData?) {
        scope.launch {
            try {
                view.showLoadingIndicator()
                passData?.matchId?.let { matchId ->
                    val matchData = getMatchDataByMatchId.invoke(matchId)
                    val gameDuration = getGameDuration(matchData?.info?.gameDuration)
                    val spellData = getSummonerAllSpellData.invoke()
                    val runeData = getRuneData.invoke()

                    val dataList = getDetailList(
                        matchData,
                        passData.winLoseFlag,
                        spellData,
                        runeData,
                        gameDuration
                    )
                    view.showDataList(dataList, passData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                view.dismissLoadingIndicator()
            }
        }
    }

    private fun getGameDuration(gameDuration: Int?): String {
        if (gameDuration == null) {
            return "00:00"
        }
        val minute = (gameDuration / 1000) / 60
        val second = (gameDuration / 1000) % 60
        return "${String.format("%02d", minute)}:${String.format("%02d", second)}"
    }

    private suspend fun getDetailList(
        matchData: Match?,
        winLoseFlag: Boolean,
        spell: Spell?,
        runeData: List<DataItem>?,
        gameDuration: String
    ): List<Any?> {
        val data = mutableListOf<Any>()

        matchData?.let { matchData ->
            val myTeamList = matchData.info?.participants?.filter { it.win == winLoseFlag }
            data.addAll(addListData(myTeamList, spell, runeData, gameDuration))
            val otherTeamList = matchData.info?.participants?.filter { it.win != winLoseFlag }
            data.addAll(addListData(otherTeamList, spell, runeData, gameDuration))
        }
        return data
    }

    private suspend fun addListData(
        list: List<Participant>?,
        spell: Spell?,
        runeData: List<DataItem>?,
        gameDuration: String
    ): MutableList<Any> {
        val dataList = mutableListOf<Any>()
        val element = mutableListOf(0, 0, 0, 0, 0, 0)

        list?.forEach { participant ->
            element[0] += participant.kills ?: 0
            element[1] += participant.deaths ?: 0
            element[2] += participant.assists ?: 0
            element[3] += participant.turretKills ?: 0
            element[4] += participant.baronKills ?: 0
            element[5] += participant.dragonKills ?: 0
        }

        // totalModel
        dataList.add(
            BindDetailTotalModel(
                teamType = list?.get(0)?.teamId,
                winFlag = list?.get(0)?.win,
                totalKill = element[0],
                totalDeath = element[1],
                totalAssist = element[2],
                towerKill = element[3],
                baronKill = element[4],
                dragonKill = element[5]
            )
        )
        val max = list?.maxByOrNull { it.totalDamageDealtToChampions ?: 0 }

        // matchModel
        list?.forEach { participant ->
            val spell1Id = spell?.getSpellIdByKey(participant.summoner1Id.toString())
            val spell2Id = spell?.getSpellIdByKey(participant.summoner2Id.toString())

            val icon1: String? = getRune1IconImageUrl.invoke(runeData, participant)
            val dataItem = runeData?.find {
                it.id == participant.perks?.styles?.get(1)?.style
            }

            val leagueData = participant.summonerId?.let { getSummonerProfileLeagueData.invoke(it) }

            dataList.add(
                BindDetailModel(
                    puuid = participant.puuid ?: "",
                    championLevel = participant.champLevel ?: 0,
                    maxDamage = max?.totalDamageDealtToChampions ?: 0,
                    championIcon = participant.championName?.let {
                        DataDragonApi.getChampionIconUrl(it)
                    },
                    minuteCs = getTimeAverageCs(gameDuration, participant),
                    spell1 = spell1Id?.let { DataDragonApi.getSummonerSpellIconUrl(it) },
                    spell2 = spell2Id?.let { DataDragonApi.getSummonerSpellIconUrl(it) },
                    rune1 = icon1?.let { DataDragonApi.getSummonerRuneImageUrl(it) },
                    rune2 = dataItem?.icon?.let { DataDragonApi.getSummonerRuneImageUrl(it) },
                    tier = leagueData?.let { getTierData(it) } ?: "",
                    summonerName = participant.summonerName ?: "",
                    kill = participant.kills ?: 0,
                    death = participant.deaths ?: 0,
                    assist = participant.assists ?: 0,
                    kda = getKda(participant),
                    items = getItemsData.invoke(participant),
                    cs = participant.totalMinionsKilled?.toString() ?: "",
                    earnedGold = getEarnGold(participant),
                    damage = participant.totalDamageDealtToChampions ?: 0
                )
            )
        }
        return dataList
    }

    private fun getTierData(data: List<ProfileLeagueItem?>) =
        if (data.size > 1) {
            val sortedList = data.sortedByDescending { it?.queueType == "RANKED_SOLO_5x5" }
            val rank = sortedList[0]?.rank ?: "Error"
            val tier = sortedList[0]?.tier ?: "Error"
            "$tier $rank"
        } else if(data.size == 1) {
            val rank = data[0]?.rank ?: "Error"
            val tier = data[0]?.tier ?: "Error"
            if (data[0]?.queueType == "RANKED_SOLO_5x5") "$tier $rank" else ""
        } else {
            ""
        }

    private fun getTimeAverageCs(gameDuration: String, participant: Participant): Float {
        val split = gameDuration.split(":")
        return if (participant.totalMinionsKilled == null || participant.totalMinionsKilled <= 0) {
            0.0F
        } else {
            val minute = split[0].toFloat()
            if (minute <= 0f) {
                0.0F
            } else {
                (participant.totalMinionsKilled / minute).let {
                    ((it * 10).roundToInt() / 10f)
                }
            }
        }
    }

    private fun getEarnGold(participant: Participant): String {
        val gold = participant.goldEarned
        return if (gold == null || gold <= 0) {
            "0.0k"
        } else {
            "${((gold / 100) / 10f)}k"
        }
    }

    private fun getKda(participant: Participant): String {
        val kill = participant.kills ?: 0
        val death = participant.deaths ?: 0
        val assist = participant.assists ?: 0

        val kda = if (death <= 0) {
            "Perfect"
        } else {
            ((kill + assist) / death.toFloat()).let {
                (it * 100).roundToInt() / 100f
            }.toString()
        }
        return kda
    }
}