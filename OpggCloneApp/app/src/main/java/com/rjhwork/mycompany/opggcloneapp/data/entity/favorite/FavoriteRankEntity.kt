package com.rjhwork.mycompany.opggcloneapp.data.entity.favorite

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class FavoriteRankEntity(
    val tier: String?,
    val rank: String?
): Parcelable