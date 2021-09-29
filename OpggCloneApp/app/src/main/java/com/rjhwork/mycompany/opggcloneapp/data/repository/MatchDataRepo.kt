package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.entity.rune.DataItem
import com.rjhwork.mycompany.opggcloneapp.data.entity.spell.Spell
import okhttp3.ResponseBody
import retrofit2.Response

interface MatchDataRepo {

    suspend fun getRequestSpell():Response<Spell>

    suspend fun getRequestRunesReforged():Response<List<DataItem>>
}