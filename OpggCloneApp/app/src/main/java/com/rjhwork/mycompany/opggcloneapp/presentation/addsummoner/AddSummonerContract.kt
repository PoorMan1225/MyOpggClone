package com.rjhwork.mycompany.opggcloneapp.presentation.addsummoner

import com.rjhwork.mycompany.opggcloneapp.BaseActivityPresenter
import com.rjhwork.mycompany.opggcloneapp.BaseActivityView

class AddSummonerContract {

    interface View: BaseActivityView<Presenter> {

        fun showDialog(name:String)

        fun finishActivityAnimation()

        fun showDialog()

        fun dismissDialog()

        fun showToast(message: String)
    }

    interface Presenter: BaseActivityPresenter {

        fun fetchSummonerProfileData(name:String)
    }
}