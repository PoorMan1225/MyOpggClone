package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepo {

    val trackingFavoriteItems: Flow<List<FavoriteEntity>>

    suspend fun getAllFavorite(): List<FavoriteEntity>

    suspend fun insertFavorite(item: FavoriteEntity)

    suspend fun deleteFavorite(item: FavoriteEntity)

    suspend fun updateFavorite(isFavorite: Boolean, summonerName: String)

    suspend fun getFilterFavoriteItems(summonerName: String) : List<FavoriteEntity>

    suspend fun getFavoriteBySummonerName(summonerName: String): FavoriteEntity
}