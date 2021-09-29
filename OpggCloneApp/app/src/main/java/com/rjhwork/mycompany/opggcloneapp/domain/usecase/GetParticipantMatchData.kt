package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Participant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetParticipantMatchData() {
    suspend operator fun invoke(match: Match?, puuid:String): Participant? = withContext(Dispatchers.Default) {
        match?.info?.participants?.find {
            it.puuid == puuid
        }
    }
}