package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import android.text.format.DateFormat
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.data.mapper.gameMode
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toMMR
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toRank
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.*
import kotlinx.coroutines.*
import java.lang.Exception

class SummonerMatchDetailPresenter(
    private val view: SummonerMatchDetailContract.View,
    private val getMatchDataByMatchId: GetMatchDataByMatchId,
    private val getSummonerProfileLeagueData: GetSummonerProfileLeagueData,
) : SummonerMatchDetailContract.Presenter {

    override var scope: CoroutineScope = MainScope()

    override fun onCreate(passData: PassData?) {
        fetchData(passData)
    }

    override fun onCreate() {}

    private fun fetchData(passData: PassData?) {
        scope.launch {
            try {
                passData?.matchId?.let { matchId ->
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
                    view.setTitleData(
                        gameMode,
                        gameDate,
                        gameDuration,
                        averageTier,
                        passData.winLoseFlag
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getAverageMatchTier(matchData: Match?): String {
        var sumTierRankMMR = 0

        matchData?.info?.participants?.forEach { participant ->
            val leagueData = participant.summonerId?.let {
                getSummonerProfileLeagueData.invoke(it)
            } ?: return ""

            val rank = if (leagueData.isNotEmpty())
                leagueData[0]?.rank ?: "Error"
            else
                "Unknown"

            val tier = if (leagueData.isNotEmpty())
                leagueData[0]?.tier ?: "Error"
            else
                "Unknown"

            sumTierRankMMR += "$tier $rank".toMMR()
        }
        return sumTierRankMMR.toRank()
    }

    private fun getGameDuration(gameDuration: Int?): String {
        if (gameDuration == null) {
            return "00:00"
        }
        val minute = (gameDuration / 1000) / 60
        val second = (gameDuration / 1000) % 60
        return "${String.format("%02d", minute)}:${String.format("%02d", second)}"
    }

    private fun getGameDate(gameCreation: Long, gameDuration: Int): CharSequence {
        val gameDate = gameCreation + gameDuration
        return DateFormat.format("yyyy.MM.dd", gameDate)
    }
}