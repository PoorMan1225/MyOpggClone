package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import android.text.format.DateFormat
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Participant
import com.rjhwork.mycompany.opggcloneapp.data.entity.rune.DataItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.spell.Spell
import com.rjhwork.mycompany.opggcloneapp.data.mapper.gameMode
import com.rjhwork.mycompany.opggcloneapp.data.mapper.getSpellIdByKey
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toMMR
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toRank
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindDetailModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindDetailTotalModel
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.math.roundToInt

class SummonerMatchDetailPresenter(
    private val view: SummonerMatchDetailContract.View,
    private val getMatchDataByMatchId: GetMatchDataByMatchId,
    private val getSummonerProfileLeagueData: GetSummonerProfileLeagueData,
    private val getSummonerAllSpellData: GetSummonerAllSpellData,
    private val getRune1IconImageUrl: GetRune1IconImageUrl,
    private val getItemsData: GetItemsData,
    private val getRuneData: GetRuneData,
) : SummonerMatchDetailContract.Presenter {

    override var scope: CoroutineScope = MainScope()

    override fun onCreate() {}

    override fun onCreate(matchId: String?, puuid: String?, winLoseFlag: Boolean) {
        fetchDataList(matchId, puuid, winLoseFlag)
    }

    private fun fetchDataList(matchId: String?, puuid: String?, winLoseFlag: Boolean) {
        scope.launch {
            try {
                view.showLoadingIndicator()
                matchId?.let { matchId ->
                    val matchData = getMatchDataByMatchId.invoke(matchId)
                    val gameMode = matchData?.info?.queueId?.gameMode() ?: "Unknown"
                    val gameDate = getGameDate(
                        matchData?.info?.gameCreation ?: 0,
                        matchData?.info?.gameDuration ?: 0
                    )
                    val gameDuration = getGameDuration(matchData?.info?.gameDuration)
                    val averageTier: String = if (gameMode == "솔랭") {
                        view.showAverageTextView()
                        getAverageMatchTier(matchData)
                    } else {
                        view.dismissAverageTextView()
                        ""
                    }
                    view.setTitleData(gameMode, gameDate, gameDuration, averageTier, winLoseFlag)
                    val spellData = getSummonerAllSpellData.invoke()
                    val runeData = getRuneData.invoke()

                    val dataList =
                        getDetailList(matchData, winLoseFlag, spellData, runeData, gameDuration)
                    view.showDataList(dataList, puuid, winLoseFlag)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                view.dismissLoadingIndicator()
            }
        }
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
            addListData(myTeamList, data, spell, runeData, gameDuration)
            val otherTeamList = matchData.info?.participants?.filter { it.win != winLoseFlag }
            addListData(otherTeamList, data, spell, runeData, gameDuration)
        }
        return data
    }

    private suspend fun addListData(
        list: List<Participant>?,
        data: MutableList<Any>,
        spell: Spell?,
        runeData: List<DataItem>?,
        gameDuration: String
    ) {
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
        data.add(
            BindDetailTotalModel(
                winFlag = list?.get(0)?.win,
                totalKill = element[0],
                totalDeath = element[1],
                totalAssist = element[2],
                towerKill = element[3],
                baronKill = element[4],
                dragonKill = element[5]
            )
        )
        val max = list?.maxByOrNull { it.physicalDamageDealtToChampions ?: 0 }

        // matchModel
        list?.forEach { participant ->
            val spell1Id = spell?.getSpellIdByKey(participant.summoner1Id.toString())
            val spell2Id = spell?.getSpellIdByKey(participant.summoner2Id.toString())

            val icon1: String? = getRune1IconImageUrl.invoke(runeData, participant)
            val dataItem = runeData?.find {
                it.id == participant.perks?.styles?.get(1)?.style
            }

            val leagueData = participant.summonerId?.let { getSummonerProfileLeagueData.invoke(it) }
            val tierData = leagueData?.let { data ->
                if (data.size > 1) {
                    "${data[1]?.tier}${data[1]?.rank}"
                } else {
                    "${data[0]?.tier}${data[0]?.rank}"
                }
            } ?: ""

            data.add(
                BindDetailModel(
                    puuid = participant.puuid ?: "",
                    championLevel = participant.champLevel ?: 0,
                    maxDamage = max?.physicalDamageDealtToChampions ?: 0,
                    championIcon = participant.championName?.let {
                        DataDragonApi.getChampionIconUrl(it)
                    },
                    minuteCs = getTimeAverageCs(gameDuration, participant),
                    spell1 = spell1Id?.let { DataDragonApi.getSummonerSpellIconUrl(it) },
                    spell2 = spell2Id?.let { DataDragonApi.getSummonerSpellIconUrl(it) },
                    rune1 = icon1?.let { DataDragonApi.getSummonerRuneImageUrl(it) },
                    rune2 = dataItem?.icon?.let { DataDragonApi.getSummonerRuneImageUrl(it) },
                    tier = tierData,
                    summonerName = participant.summonerName ?: "",
                    kill = participant.kills ?: 0,
                    death = participant.deaths ?: 0,
                    assist = participant.assists ?: 0,
                    kda = getKda(participant),
                    items = getItemsData.invoke(participant),
                    cs = participant.totalMinionsKilled?.toString() ?: "",
                    earnedGold = getEarnGold(participant),
                    damage = participant.physicalDamageDealtToChampions ?: 0
                )
            )
        }
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

    private suspend fun getAverageMatchTier(matchData: Match?): String {
        var sumTierRankMMR: Int = 0

        matchData?.info?.participants?.forEach { participant ->
            val leagueData = participant.summonerId?.let {
                getSummonerProfileLeagueData.invoke(it)
            } ?: return ""

            sumTierRankMMR += if(leagueData.size > 1) {
                val rank = leagueData[1]?.rank ?: "Unknown"
                val tier = leagueData[1]?.tier ?: "Unknown"
                "$tier $rank".toMMR()
            } else {
                val rank = leagueData[0]?.rank ?: "Unknown"
                val tier = leagueData[0]?.tier ?: "Unknown"
                "$tier $rank".toMMR()
            }
        }
        return sumTierRankMMR.toRank()
    }

    private fun getGameDuration(gameDuration: Int?): String {
        if (gameDuration == null) {
            return "00:00"
        }
        val minute = (gameDuration / 1000) / 60
        val second = (gameDuration / 1000) % 60
        return "${String.format("02d", minute)}:${String.format("02d", second)}"
    }

    private fun getGameDate(gameCreation: Long, gameDuration: Int): CharSequence {
        val gameDate = gameCreation + gameDuration
        return DateFormat.format("yyyy.MM.dd", gameDate)
    }
}