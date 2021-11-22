package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.domain.model.AnalysisModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindAnalysisData
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.analysisfragment.AnalysisDataFragment

class MatchAnalysisAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var pair: Pair<String, Match?>? = null
    var list: List<BindAnalysisData>? = null

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> AnalysisDataFragment.newInstance(
                AnalysisModel(
                    getSortedList(list, position),
                    pair?.first,
                    position,
                    getSumByCondition(list,{ data -> data.win == true},{ data -> data.kills ?: 0 }),
                    getSumByCondition(list,{ data -> data.win == false},{ data -> data.kills ?: 0 })
                )
            )
            1 -> AnalysisDataFragment.newInstance(
                AnalysisModel(
                    getSortedList(list, position),
                    pair?.first,
                    position,
                    getSumByCondition(list,{ data -> data.win == true},{ data -> data.goldEarned ?: 0 }),
                    getSumByCondition(list,{ data -> data.win == false},{ data -> data.goldEarned ?: 0 })
                )
            )
            2 ->  AnalysisDataFragment.newInstance(
                AnalysisModel(
                    getSortedList(list, position),
                    pair?.first,
                    position,
                    getSumByCondition(list,{ data -> data.win == true},{ data -> data.totalDamageDealtToChampions ?: 0 }),
                    getSumByCondition(list,{ data -> data.win == false},{ data -> data.totalDamageDealtToChampions ?: 0 })
                )
            )
            3 ->  AnalysisDataFragment.newInstance(
                AnalysisModel(
                    getSortedList(list, position),
                    pair?.first,
                    position,
                    getSumByCondition(list,{ data -> data.win == true},{ data -> data.totalDamageShieldedOnTeammates ?: 0 }),
                    getSumByCondition(list,{ data -> data.win == false},{ data -> data.totalDamageShieldedOnTeammates ?: 0 })
                )
            )
            4 -> AnalysisDataFragment.newInstance(
                AnalysisModel(
                    getSortedList(list, position),
                    pair?.first,
                    position,
                    getSumByCondition(list,{ data -> data.win == true},{ data -> data.totalMinionsKilled ?: 0 }),
                    getSumByCondition(list,{ data -> data.win == false},{ data -> data.totalMinionsKilled ?: 0 })
                )
            )
            5 -> AnalysisDataFragment.newInstance(
                AnalysisModel(
                    getSortedList(list, position),
                    pair?.first,
                    position,
                    getSumByCondition(list,{ data -> data.win == true},{ data -> data.wardsPlaced ?: 0 }),
                    getSumByCondition(list,{ data -> data.win == false},{ data -> data.wardsPlaced ?: 0 })
                )
            )
            else -> throw Exception("있을 수 없는 ViewPager Position 입니다.")
        }
    }

    private fun getSortedList(
        dataList: List<BindAnalysisData>?,
        position: Int?
    ): List<BindAnalysisData> {
        if (dataList == null) return emptyList()

        return when (position) {
            0 -> dataList.sortedByDescending { it.kills ?: 0 }
            1 -> dataList.sortedByDescending { it.goldEarned ?: 0 }
            2 -> dataList.sortedByDescending { it.totalDamageDealtToChampions ?: 0 }
            3 -> dataList.sortedByDescending { it.totalDamageShieldedOnTeammates ?: 0 }
            4 -> dataList.sortedByDescending { it.totalMinionsKilled ?: 0 }
            5 -> dataList.sortedByDescending { it.wardsPlaced ?: 0 }
            else -> emptyList()
        }
    }

    private fun getSumByCondition(
        list:List<BindAnalysisData>?,
        condition1: (BindAnalysisData) -> Boolean,
        condition2: (BindAnalysisData) -> Int
    ): Int {
        if(list == null)
            return 0
        return list.filter(condition1).sumOf(condition2)
    }

    fun getDataList() = pair?.second?.info?.participants?.map {
        BindAnalysisData(
            puuid = it.puuid,
            win = it.win,
            kills = it.kills,
            championName = it.championName,
            goldEarned = it.goldEarned,
            totalDamageDealtToChampions = it.totalDamageDealtToChampions,
            totalMinionsKilled = it.totalMinionsKilled,
            totalDamageShieldedOnTeammates = it.totalDamageShieldedOnTeammates,
            wardsPlaced = it.wardsPlaced
        )
    }
}
