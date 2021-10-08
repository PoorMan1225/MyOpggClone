package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import android.annotation.SuppressLint
import android.util.Log
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Participant
import com.rjhwork.mycompany.opggcloneapp.data.mapper.gameMode
import com.rjhwork.mycompany.opggcloneapp.data.mapper.getSpellIdByKey
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindMatchModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.KillDeathAssist
import com.rjhwork.mycompany.opggcloneapp.domain.model.RecentAllKdaModel
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@SuppressLint("SimpleDateFormat")
val format = SimpleDateFormat("yyyy.MM.dd")

class GetMatchDataDetail(
    private val getSummonerMatchesList: GetSummonerMatchesList,
    private val getParticipantMatchData: GetParticipantMatchData,
    private val getSummonerAllSpellData: GetSummonerAllSpellData,
    private val getItemsData: GetItemsData,
    private val getRuneData: GetRuneData,
    private val getMatchDataByMatchId: GetMatchDataByMatchId,
    private val getKillAssistRate: GetKillAssistRate,
    private val getRune1IconImageUrl: GetRune1IconImageUrl
) {
    suspend operator fun invoke(puuid: String, index: Int = 0): Pair<List<BindMatchModel>, RecentAllKdaModel> =
        withContext(Dispatchers.Default) {

            val matchList = getSummonerMatchesList.invoke(puuid, index)
            val itemModel = mutableListOf<BindMatchModel>()
            val spellData = getSummonerAllSpellData.invoke()
            val runData = getRuneData.invoke()
            val recentAllKdaModel = RecentAllKdaModel()

            matchList?.forEach {  matchId ->
                val match = getMatchDataByMatchId.invoke(matchId)
                val mode = match?.info?.queueId?.gameMode()
                val participant = getParticipantMatchData.invoke(match, puuid)
                val totalKillAssist = getKillAssistRate.invoke(match, participant?.win)

                val myKillAssist = if (participant?.kills != null && participant.assists != null) {
                    participant.kills + participant.assists
                } else {
                    0
                }

                val killAssistRate = if (myKillAssist != 0) {
                    (myKillAssist / totalKillAssist.toFloat() * 100).roundToInt()
                } else {
                    0
                }

                setRecentDataModel(recentAllKdaModel, participant, killAssistRate)
                val matchDuration = getMatchDuration(match?.info?.gameStartTimestamp, match?.info?.gameEndTimestamp)

                participant?.let { participant ->
                    val championIcon =
                        participant.championName?.let { DataDragonApi.getChampionIconUrl(it) }
                    val spell1Id = spellData?.getSpellIdByKey(participant.summoner1Id.toString())
                    val spell2Id = spellData?.getSpellIdByKey(participant.summoner2Id.toString())
                    val spell1Url = spell1Id?.let { DataDragonApi.getSummonerSpellIconUrl(it) }
                    val spell2Url = spell2Id?.let { DataDragonApi.getSummonerSpellIconUrl(it) }

                    val items = getItemsData.invoke(participant)

                    val icon1: String? = getRune1IconImageUrl.invoke(runData, participant)
                    val dataItem = runData?.find {
                        it.id == participant.perks?.styles?.get(1)?.style
                    }

                    val rune1Url = icon1?.let { DataDragonApi.getSummonerRuneImageUrl(it) }
                    val rune2Url = dataItem?.icon?.let { DataDragonApi.getSummonerRuneImageUrl(it) }

                    val sumMills =
                        if (match?.info?.gameCreation != null && match.info.gameDuration != null) {
                            match.info.gameCreation + match.info.gameDuration
                        } else {
                            System.currentTimeMillis()
                        }

                    val date = format.format(Date(sumMills))

                    itemModel.add(
                        BindMatchModel(
                            puuid = puuid,
                            matchId = matchId,
                            win = participant.win,
                            date = date,
                            gameMode = mode,
                            matchDuration = matchDuration,
                            championIcon = championIcon,
                            spell1Icon = spell1Url,
                            spell2Icon = spell2Url,
                            rune1Icon = rune1Url,
                            rune2Icon = rune2Url,
                            time = match?.info?.gameDuration?.toLong()?.let {
                                match.info.gameStartTimestamp?.plus(it)
                            },
                            killDeathAssist = KillDeathAssist(
                                killAssistRate = killAssistRate,
                                kill = participant.kills,
                                death = participant.deaths,
                                assist = participant.assists,
                                pentaKills = participant.pentaKills,
                                quadraKills = participant.quadraKills,
                                tripleKills = participant.tripleKills,
                                doubleKills = participant.doubleKills
                            ),
                            items = items
                        )
                    )
                }
            }
            itemModel to recentAllKdaModel
        }

    private fun setRecentDataModel(
        recentAllKdaModel: RecentAllKdaModel,
        participant: Participant?,
        rate: Int
    ) {
        recentAllKdaModel.apply {
            kills += participant?.kills ?: 0
            deaths += participant?.deaths ?: 0
            assists += participant?.assists ?: 0
            win += if (participant?.win == true) 1 else 0
            lose += if (participant?.win == true) 0 else 1
            killAssistRate += rate
        }
    }

    private fun getMatchDuration(gameStart:Long?, gameEnd:Long?): String {
        if(gameStart == null || gameEnd == null) {
            return "00:00"
        }
        val gameDuration = gameEnd - gameStart
        val minute = (gameDuration / 1000) / 60
        val second = (gameDuration / 1000) % 60
        return "${String.format("%02d", minute)}:${String.format("%02d", second)}"
    }
}