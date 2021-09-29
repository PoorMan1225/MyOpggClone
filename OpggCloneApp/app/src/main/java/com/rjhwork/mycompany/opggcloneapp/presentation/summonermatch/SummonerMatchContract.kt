package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch

import com.rjhwork.mycompany.opggcloneapp.BaseActivityPresenter
import com.rjhwork.mycompany.opggcloneapp.BaseActivityView
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindMatchModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileMostChampionModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.RecentAllKdaModel

class SummonerMatchContract {

    interface View : BaseActivityView<Presenter> {

        fun showLoadingIndicator()

        fun dismissLoadingIndicator()

        fun showListData(
            pairList: Pair<List<BindMatchModel?>, RecentAllKdaModel>,
            matchMostData: ProfileMostChampionModel?
        )

        fun showFavoriteDeleteDialog(favoriteEntity: FavoriteEntity)

        fun showProfile(favoriteEntity: FavoriteEntity, value: String?)

        fun setFavoriteImageView(value: String?)

        fun showViewHolderProgress()

        fun addListData(pairList: Pair<List<BindMatchModel?>, RecentAllKdaModel>)

        fun showLeagueData(summonerLeagueData: List<ProfileLeagueItem?>?)

        fun dismissRefreshProgress()

        fun showRefreshProgress()

        fun enableRefreshClick()

        fun disableRefreshClick()

        fun showTimeLimitDialog(timeMills: Long, currentMills: Long)
    }

    interface Presenter : BaseActivityPresenter {

        fun onCreate(favoriteEntity: FavoriteEntity?, value: String?)

        fun updateFavoriteData(favoriteEntity: FavoriteEntity)

        fun showMoreList(favoriteEntity: FavoriteEntity, index: Int): Int

        fun showRefreshList(favoriteEntity: FavoriteEntity)
    }
}