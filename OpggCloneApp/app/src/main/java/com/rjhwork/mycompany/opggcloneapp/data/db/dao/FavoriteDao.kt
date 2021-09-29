package com.rjhwork.mycompany.opggcloneapp.data.db.dao

import androidx.room.*
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM FavoriteEntity")
    suspend fun getAll(): List<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity")
    fun allTrackingItems(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteItem: FavoriteEntity)

    @Delete
    suspend fun delete(favoriteItem: FavoriteEntity)

    @Query("UPDATE FavoriteEntity SET isFavorite =:isFavorite WHERE summonerName =:summonerName")
    suspend fun updateFavorite(isFavorite: Boolean, summonerName: String)

    @Query("SELECT * FROM FavoriteEntity WHERE summonerName !=:summonerName AND isFavorite =:isFavorite")
    suspend fun getFavoriteTrueExSummonerName(summonerName: String, isFavorite: Boolean): List<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity WHERE summonerName =:summonerName")
    suspend fun getFavoriteBySummonerName(summonerName: String): FavoriteEntity
}