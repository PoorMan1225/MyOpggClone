package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.databinding.FragmentMatchAnalysisBinding
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.SummonerMatchDetailActivity
import org.koin.android.scope.ScopeFragment

class MatchAnalysisFragment: ScopeFragment(), MatchAnalysisContract.View {

    private var binding: FragmentMatchAnalysisBinding? = null

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
        presenter.onViewCreated(arguments?.getParcelable(SummonerMatchDetailActivity.PASS_DATA_KEY))
    }

    private fun initViews() = Unit

    private fun bindViews() = Unit

    @SuppressLint("NotifyDataSetChanged")
    override fun setAdapterData(pair: Pair<String, Match?>) {
        Log.d("MatchAnalysisFragment", "여기까진 왔습니다. ")
        repeat(6) {
            binding?.let {
                it.tabLayout.addTab(it.tabLayout.newTab())
            }
        }

        binding?.viewPager?.adapter = MatchAnalysisAdapter(this, ).apply {
            this.pair = pair
        }
        binding?.let {
            TabLayoutMediator(it.tabLayout, it.viewPager) { tab, position ->
                when(position) {
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