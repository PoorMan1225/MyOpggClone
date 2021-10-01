package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import com.rjhwork.mycompany.opggcloneapp.BaseActivityPresenter
import com.rjhwork.mycompany.opggcloneapp.BaseActivityView
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData

class SummonerMatchDetailContract {

    interface View: BaseActivityView<Presenter> {

        fun showAverageTextView()

        fun dismissAverageTextView()

        fun setTitleData(gameMode: String, gameDate: CharSequence, gameDuration: String, averageTier: String, winLoseFlag: Boolean)
    }

    interface Presenter: BaseActivityPresenter {

        fun onCreate(passData: PassData?)
    }
}