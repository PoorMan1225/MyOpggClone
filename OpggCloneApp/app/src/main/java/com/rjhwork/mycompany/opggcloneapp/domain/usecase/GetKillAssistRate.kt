package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetKillAssistRate() {

    suspend operator fun invoke(match: Match?, win: Boolean?): Int = withContext(Dispatchers.Default) {
        var totalKillAssist: Int = 0

        match?.info?.participants?.forEach { participant ->
            if (win != null && win != participant.win) {
                totalKillAssist += participant.deaths ?: 0
            }
        }
        totalKillAssist
    }
}