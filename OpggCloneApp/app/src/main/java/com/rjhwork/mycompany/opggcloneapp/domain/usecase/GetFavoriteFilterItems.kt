package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import android.util.Log
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.repository.FavoriteRepo

class GetFavoriteFilterItems(
    private val favoriteRepo: FavoriteRepo
) {
    suspend operator fun invoke(summonerName: String): List<FavoriteEntity>  {
        return favoriteRepo.getFilterFavoriteItems(summonerName)
    }
}