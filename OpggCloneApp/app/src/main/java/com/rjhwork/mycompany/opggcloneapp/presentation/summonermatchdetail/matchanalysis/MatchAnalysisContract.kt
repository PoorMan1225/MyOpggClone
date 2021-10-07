package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis

import com.rjhwork.mycompany.opggcloneapp.BasePresenter
import com.rjhwork.mycompany.opggcloneapp.BaseView
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData

class MatchAnalysisContract {

    interface View: BaseView<Presenter> {

        fun showLoadingIndicator()

        fun dismissLoadingIndicator()

        fun setAdapterData(pair:Pair<String, Match?>)
    }

    interface Presenter: BasePresenter {

        fun onViewCreated(passData: PassData?)

    }
}