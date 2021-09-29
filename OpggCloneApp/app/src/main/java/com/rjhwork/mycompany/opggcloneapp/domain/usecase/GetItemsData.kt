package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Participant
import com.rjhwork.mycompany.opggcloneapp.domain.model.Items
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetItemsData() {

    operator fun invoke(participant: Participant): Items {
        val item0 = DataDragonApi.getRequestItemImageUrl(participant.item0.toString())
        val item1 = DataDragonApi.getRequestItemImageUrl(participant.item1.toString())
        val item2 = DataDragonApi.getRequestItemImageUrl(participant.item2.toString())
        val item3 = DataDragonApi.getRequestItemImageUrl(participant.item3.toString())
        val item4 = DataDragonApi.getRequestItemImageUrl(participant.item4.toString())
        val item5 = DataDragonApi.getRequestItemImageUrl(participant.item5.toString())
        val item6 = DataDragonApi.getRequestItemImageUrl(participant.item6.toString())

        return Items(item0, item1, item2, item3, item4, item5, item6)
    }
}