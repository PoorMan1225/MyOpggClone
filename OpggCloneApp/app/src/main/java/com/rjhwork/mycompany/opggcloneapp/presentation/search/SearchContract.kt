package com.rjhwork.mycompany.opggcloneapp.presentation.search

import com.rjhwork.mycompany.opggcloneapp.BasePresenter
import com.rjhwork.mycompany.opggcloneapp.BaseView
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindProfileModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileMostChampionModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileRank

class SearchContract {

    interface View: BaseView<Presenter> {

        fun showAddSummonerProfile(prfile:BindProfileModel)

        fun deleteAddSummonerProfile()

        fun touchEnabledFalse()

        fun touchEnabledTrue()

        fun showEmptyRankLayout(profileRank: ProfileRank)

        fun showToast(message: String)

        fun showLoadingIndicator()

        fun dismissLoadingIndicator()

        fun showFavoriteLoadingIndicator()

        fun dismissFavoriteLoadingIndicator()

        fun showEmptyFavoriteLayout()

        fun showFavoriteDataLayout()

        fun showFavoriteDataList(favoriteList: List<FavoriteEntity>)

        fun showEmptyChampionDataLayout(profileMostChampionModel: ProfileMostChampionModel?)

        fun startMatchActivityWithAnimation(favoriteEntity: FavoriteEntity, value: String?)
    }

    interface Presenter: BasePresenter {
        fun retry(dataCheck: Boolean)

        fun getFavoriteBySummonerName(summonerName: String)
    }
}