package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.analysisfragment.AnalysisDataFragment

class MatchAnalysisAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    lateinit var match: Match

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        val fragment = AnalysisDataFragment()

        if(::match.isInitialized) {
            
        }
        fragment.arguments = Bundle().apply {

        }
        return fragment
    }
}