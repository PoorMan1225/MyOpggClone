package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import android.util.Log
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Participant
import com.rjhwork.mycompany.opggcloneapp.data.entity.rune.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRune1IconImageUrl {

    suspend operator fun invoke(runeData: List<DataItem>?, participant: Participant): String? =
        withContext(Dispatchers.Default) {
            val dataItem = runeData?.find { it.id == participant.perks?.styles?.get(0)?.style }
            dataItem?.slots?.get(0)?.runes?.find {
                it.id == participant.perks?.styles?.get(0)?.selections?.get(0)?.perk
            }?.icon
        }
}