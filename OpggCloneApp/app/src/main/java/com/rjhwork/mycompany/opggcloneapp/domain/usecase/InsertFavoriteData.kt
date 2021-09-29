package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.repository.FavoriteRepo
import kotlinx.coroutines.coroutineScope

class InsertFavoriteData(
    private val favoriteRepo: FavoriteRepo
) {
    suspend operator fun invoke(item: FavoriteEntity) {
        favoriteRepo.insertFavorite(item)
    }
}