package com.rjhwork.mycompany.opggcloneapp.data.mapper

import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Participant
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileChampionModel

fun Participant.toProfileChampionModel() = ProfileChampionModel(
    championName = championName
).apply {
    if (win != null) {
        winFlag = win
    }
    if (kills != null) {
        kill = kills
    }
    if (assists != null) {
        assist = assists
    }
    if (deaths != null) {
        death = deaths
    }
}