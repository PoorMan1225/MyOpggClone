package com.rjhwork.mycompany.opggcloneapp.presentation.ranking

import com.rjhwork.mycompany.opggcloneapp.BasePresenter
import com.rjhwork.mycompany.opggcloneapp.BaseView
import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingEntity

class RankingContract {

    interface View: BaseView<Presenter> {

        fun fetchRankingData(list: List<RankingEntity>, count:Int)

        fun showLoadingIndicator()

        fun dismissLoadingIndicator()

        fun addRankingList(list: List<RankingEntity>, count: Int)
    }

    interface Presenter: BasePresenter {

        fun showMoreRankingData()
    }
}