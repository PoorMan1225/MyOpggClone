package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.rune.DataItem
import com.rjhwork.mycompany.opggcloneapp.data.repository.MatchDataRepo
import kotlinx.coroutines.coroutineScope

class GetRuneData(
    private val matchRepo: MatchDataRepo
) {
    suspend operator fun invoke():List<DataItem>? {
        return matchRepo.getRequestRunesReforged().body()
    }
}