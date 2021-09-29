package com.rjhwork.mycompany.opggcloneapp.domain.usecase

import com.rjhwork.mycompany.opggcloneapp.data.repository.FavoriteRepo

class GetTrackingFavoriteData(
    private val favoriteRepo: FavoriteRepo
) {
    operator fun invoke() = favoriteRepo.trackingFavoriteItems
}