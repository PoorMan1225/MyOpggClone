package com.rjhwork.mycompany.opggcloneapp.data.entity.favorite

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEntity(
    @PrimaryKey
    val summonerName: String,
    val summonerPuuid: String?,
    val summonerIcon: String?,
    val summonerLevel: String?,
    var isFavorite: Boolean = false,
    @Embedded
    val profileRank: FavoriteRankEntity? = null
): Parcelable