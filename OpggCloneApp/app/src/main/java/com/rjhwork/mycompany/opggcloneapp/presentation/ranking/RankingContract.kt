package com.rjhwork.mycompany.opggcloneapp.presentation.ranking

import com.rjhwork.mycompany.opggcloneapp.BasePresenter
import com.rjhwork.mycompany.opggcloneapp.BaseView
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.domain.model.RankingModel

class RankingContract {

    interface View: BaseView<Presenter> {

        fun fetchRankingData(list: List<RankingModel>?)

        fun showLoadingIndicator()

        fun dismissLoadingIndicator()

        fun addRankingList(list: List<RankingModel>?)

        fun noDataRankingList()

        fun startMatchActivityWithAnimation(favoriteEntity: FavoriteEntity, value: String?)
    }

    interface Presenter: BasePresenter {

        fun showMoreRankingData()

        fun getFavoriteBySummonerName(summonerName: String)
    }
}