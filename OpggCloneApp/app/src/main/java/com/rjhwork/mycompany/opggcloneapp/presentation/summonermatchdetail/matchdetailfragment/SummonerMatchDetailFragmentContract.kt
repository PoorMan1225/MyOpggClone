package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchdetailfragment

import com.rjhwork.mycompany.opggcloneapp.BasePresenter
import com.rjhwork.mycompany.opggcloneapp.BaseView
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData

class SummonerMatchDetailFragmentContract {

    interface View: BaseView<Presenter> {

        fun showLoadingIndicator()

        fun dismissLoadingIndicator()

        fun showDataList(dataList:List<Any?>, passData: PassData)
    }

    interface Presenter: BasePresenter {

        fun onViewCreated(passData: PassData?)
    }
}