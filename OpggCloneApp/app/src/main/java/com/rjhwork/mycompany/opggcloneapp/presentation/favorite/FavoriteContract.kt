package com.rjhwork.mycompany.opggcloneapp.presentation.favorite

import com.rjhwork.mycompany.opggcloneapp.BaseActivityPresenter
import com.rjhwork.mycompany.opggcloneapp.BaseActivityView
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity

class FavoriteContract {

    interface View: BaseActivityView<Presenter> {

        fun showNoDataDescription()

        fun showTrackingFavoriteData(items:List<FavoriteEntity>)

        fun showFavoriteDeleteDialog(favoriteEntity: FavoriteEntity)

        fun showDialog(name:String)

        fun showLoadingIndicator()

        fun dismissLoadingIndicator()

        fun startToMatchListActivityWithAnimation(favoriteEntity: FavoriteEntity)
    }

    interface Presenter : BaseActivityPresenter {

        val trackingFavoriteItem: List<FavoriteEntity>

        fun updateFavoriteData(favoriteEntity: FavoriteEntity)

        fun deleteFavoriteData(favoriteEntity: FavoriteEntity)

        fun requestFavoriteData(summonerName: String)
    }
}