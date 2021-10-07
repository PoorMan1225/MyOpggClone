package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis

import android.util.Log
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.GetMatchDataByMatchId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MatchAnalysisPresenter(
    private val view:MatchAnalysisContract.View,
    private val getMatchDataByMatchId: GetMatchDataByMatchId
): MatchAnalysisContract.Presenter {

    override var scope: CoroutineScope = MainScope()

    override fun onViewCreated(passData: PassData?) {
        passData?.let {
            scope.launch {
                view.showLoadingIndicator()
                try{
                    val match = passData.matchId.let { getMatchDataByMatchId.invoke(it) }
                    view.setAdapterData(Pair(passData.summonerPuuid, match))
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    view.dismissLoadingIndicator()
                }
            }
        }
    }

    override fun onViewCreated() {}

    override fun onDestroyView() {}
}