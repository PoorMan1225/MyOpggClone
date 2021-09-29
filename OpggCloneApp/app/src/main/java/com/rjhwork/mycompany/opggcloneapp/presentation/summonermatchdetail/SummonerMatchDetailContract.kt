package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import com.rjhwork.mycompany.opggcloneapp.BaseActivityPresenter
import com.rjhwork.mycompany.opggcloneapp.BaseActivityView

class SummonerMatchDetailContract {

    interface View: BaseActivityView<Presenter> {

        fun showLoadingIndicator()

        fun dismissLoadingIndicator()

        fun showAverageTextView()

        fun dismissAverageTextView()

        fun setTitleData(gameMode: String, gameDate: CharSequence, gameDuration: String, averageTier: String, winLoseFlag: Boolean)

        fun showDataList(dataList:List<Any?>, puuid: String?, winLoseFlag: Boolean)
    }

    interface Presenter: BaseActivityPresenter {

        fun onCreate(matchId: String?, puuid: String?, winLoseFlag:Boolean)
    }
}