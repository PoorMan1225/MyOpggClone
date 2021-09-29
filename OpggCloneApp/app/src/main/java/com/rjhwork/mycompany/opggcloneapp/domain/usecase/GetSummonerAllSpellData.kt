package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.spell.Spell
import com.rjhwork.mycompany.opggcloneapp.data.repository.MatchDataRepo
import kotlinx.coroutines.coroutineScope

class GetSummonerAllSpellData(
    private val matchDataRepo: MatchDataRepo
) {
    suspend operator fun invoke(): Spell? {
        return matchDataRepo.getRequestSpell().body()
    }
}