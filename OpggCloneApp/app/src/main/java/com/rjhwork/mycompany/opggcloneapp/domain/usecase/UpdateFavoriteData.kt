package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.repository.FavoriteRepo
import kotlinx.coroutines.coroutineScope

class UpdateFavoriteData(
    private val favoriteRepo: FavoriteRepo
) {

    suspend operator fun invoke(favoriteEntity: FavoriteEntity) {
        if(favoriteEntity.isFavorite == null) {
            return
        }
        favoriteRepo.updateFavorite(favoriteEntity.isFavorite!!.not(), favoriteEntity.summonerName)
    }
}