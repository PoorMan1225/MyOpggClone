package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchdetailfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.databinding.FragmentSummonerMatchDetailBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.SummonerMatchDetailActivity.Companion.PASS_DATA_KEY
import org.koin.android.scope.ScopeFragment

class SummonerMatchDetailFragment(): ScopeFragment(), SummonerMatchDetailFragmentContract.View {

    override val presenter: SummonerMatchDetailFragmentContract.Presenter by inject()
    private var binding: FragmentSummonerMatchDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSummonerMatchDetailBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.onViewCreated(arguments?.getParcelable(PASS_DATA_KEY))
    }

    private fun initViews() {
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = SummonerMatchDetailAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoadingIndicator() {
        binding?.progressBar?.isVisible = true
    }

    override fun dismissLoadingIndicator() {
        binding?.progressBar?.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showDataList(dataList: List<Any?>, passData: PassData) {
        (binding?.recyclerView?.adapter as SummonerMatchDetailAdapter).apply {
            data = dataList
            this.passData = passData.copy(
                matchId = passData.matchId,
                summonerPuuid = passData.summonerPuuid,
                winLoseFlag = passData.winLoseFlag
            )
            notifyDataSetChanged()
        }
    }

    companion object {
        const val TAG = "SummonerMatchDetailFragment"
    }
}