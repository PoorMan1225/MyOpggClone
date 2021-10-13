package com.rjhwork.mycompany.opggcloneapp.presentation.ranking

import com.rjhwork.mycompany.opggcloneapp.domain.usecase.GetChallengerRankingData
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.GetGrandMasterRankingData
import com.rjhwork.mycompany.opggcloneapp.domain.usecase.GetMasterRankingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class RankingPresenter(
    private var view: RankingContract.View,
    private val getChallengerRankingData: GetChallengerRankingData,
    private val getGrandMasterRankingData: GetGrandMasterRankingData,
    private val getMasterRankingData: GetMasterRankingData
): RankingContract.Presenter {

    override var scope: CoroutineScope = MainScope()
    var count = 1

    override fun onViewCreated() {
        fetchDataList()
    }

    private fun fetchDataList() {
        scope.launch {
            try{
                view.showLoadingIndicator()
                val list = getChallengerRankingData.invoke()
                list?.let {
                    view.fetchRankingData(it, count)
                }
            }catch (e:Exception) {
                e.printStackTrace()
            }finally {
                view.dismissLoadingIndicator()
            }
        }
    }

    override fun showMoreRankingData() {
        scope.launch {
            when (count) {
                1 -> getDataProcess()
                2 -> getDataProcess()
            }
        }
    }

    private suspend fun getDataProcess() {
        try {
            count += 1
            val list = when (count) {
                2 -> getGrandMasterRankingData.invoke()
                3 -> getMasterRankingData.invoke()
                else -> return
            }
            list?.let {
                view.addRankingList(it, count)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            count -= 1
        }
    }


    override fun onDestroyView() {}

}