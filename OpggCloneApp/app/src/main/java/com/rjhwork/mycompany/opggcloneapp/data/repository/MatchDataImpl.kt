package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.api.DataDragonApi
import com.rjhwork.mycompany.opggcloneapp.data.entity.rune.DataItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.spell.Spell
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

class MatchDataImpl(
    private val dispatcher: CoroutineDispatcher,
    private val dataDragonApi: DataDragonApi
): MatchDataRepo {

    override suspend fun getRequestSpell(): Response<Spell> = withContext(dispatcher) {
        dataDragonApi.getRequestSummonerSpells()
    }

    override suspend fun getRequestRunesReforged(): Response<List<DataItem>> = withContext(dispatcher) {
        dataDragonApi.getRequestSummonerRuns()
    }
}