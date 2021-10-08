package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.databinding.FragmentMatchAnalysisBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.SummonerMatchDetailActivity
import org.koin.android.scope.ScopeFragment

class MatchAnalysisFragment : ScopeFragment(), MatchAnalysisContract.View {

    private var binding: FragmentMatchAnalysisBinding? = null
    private var passData: PassData? = null
    override val presenter: MatchAnalysisContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMatchAnalysisBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
        presenter.onViewCreated(passData)
    }

    private fun initViews() {
        passData = arguments?.getParcelable(SummonerMatchDetailActivity.PASS_DATA_KEY)

        repeat(6) {
            binding?.let {
                it.tabLayout.addTab(it.tabLayout.newTab())
            }
        }

        passData?.let { data ->
            binding?.tabLayout?.setSelectedTabIndicatorColor(
                getWinLoseColor(data)
            )
            binding?.tabLayout?.setTabTextColors(
                getColor(requireContext(), R.color.darker_gray),
                getWinLoseColor(data)
            )
        }
    }

    private fun bindViews() = Unit

    override fun setAdapterData(pair: Pair<String, Match?>) {
        Log.d("MainActivity", "puuid: ${pair.first}")

        binding?.viewPager?.adapter = MatchAnalysisAdapter(this).apply {
            this.pair = pair
        }

        binding?.let {
            TabLayoutMediator(it.tabLayout, it.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.tab1)
                    1 -> tab.text = getString(R.string.tab2)
                    2 -> tab.text = getString(R.string.tab3)
                    3 -> tab.text = getString(R.string.tab4)
                    4 -> tab.text = getString(R.string.tab5)
                    5 -> tab.text = getString(R.string.tab6)
                }
            }.attach()
        }
    }

    private fun getWinLoseColor(data: PassData) =
        if (data.winLoseFlag)
            getColor(
                requireContext(),
                R.color.win_background
            ) else {
            getColor(
                requireContext(),
                R.color.lose_background
            )
        }

    override fun showLoadingIndicator() {
        binding?.progressBar?.isVisible = true
    }

    override fun dismissLoadingIndicator() {
        binding?.progressBar?.isVisible = false
    }

    companion object {
        const val TAG = "MatchAnalysisFragment"
    }
}