package com.rjhwork.mycompany.opggcloneapp.data.repository

import com.rjhwork.mycompany.opggcloneapp.data.db.dao.FavoriteDao
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FavoriteRepoImpl(
    private val dispatcher: CoroutineDispatcher,
    private val favoriteDao: FavoriteDao
): FavoriteRepo {

    override val trackingFavoriteItems: Flow<List<FavoriteEntity>> =
        favoriteDao.allTrackingItems()
            .distinctUntilChanged()
            .flowOn(dispatcher)

    override suspend fun getAllFavorite(): List<FavoriteEntity> = withContext(dispatcher) {
        favoriteDao.getAll()
    }

    override suspend fun insertFavorite(item: FavoriteEntity) = withContext(dispatcher) {
        favoriteDao.insert(item)
    }

    override suspend fun deleteFavorite(item: FavoriteEntity) = withContext(dispatcher) {
        favoriteDao.delete(item)
    }

    override suspend fun updateFavorite(isFavorite: Boolean, summonerName: String) = withContext(dispatcher){
        favoriteDao.updateFavorite(isFavorite, summonerName)
    }

    override suspend fun getFilterFavoriteItems(summonerName: String): List<FavoriteEntity> = withContext(dispatcher) {
        favoriteDao.getFavoriteTrueExSummonerName(summonerName, true)
    }

    override suspend fun getFavoriteBySummonerName(summonerName: String): FavoriteEntity = withContext(dispatcher) {
        favoriteDao.getFavoriteBySummonerName(summonerName)
    }
}