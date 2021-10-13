package com.rjhwork.mycompany.opggcloneapp.presentation.ranking

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingEntity
import com.rjhwork.mycompany.opggcloneapp.databinding.FragmentRankingBinding
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch.SummonerMatchAdapter
import org.koin.android.scope.ScopeFragment

class RankingFragment : ScopeFragment(), RankingContract.View {

    private var binding: FragmentRankingBinding? = null
    private var isLoading = false

    override val presenter: RankingContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRankingBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    private fun initViews() {
        binding?.recyclerView?.apply {
            addItemDecoration(DividerItemDecoration(this.context, 1))
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }
    }

    private fun bindViews() {
        binding?.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManger = (binding?.recyclerView?.layoutManager as LinearLayoutManager)
                if (!isLoading) {
                    if (layoutManger.findLastCompletelyVisibleItemPosition()
                        == ((binding?.recyclerView?.adapter as RankingAdapter).data.size - 1)
                    ) {
                        showViewHolderProgress()
                        isLoading = true
                    }
                }
            }
        })
    }

    fun showViewHolderProgress() {
        (binding?.recyclerView?.adapter as RankingAdapter).apply {
            data.add(RankingAdapter.DataItem(null))
            notifyItemInserted(data.size - 1)
        }
        isLoading = true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun addRankingList(list: List<RankingEntity>, count: Int) {
        (binding?.recyclerView?.adapter as RankingAdapter).apply {
            data.removeAt(data.size - 1)
            notifyItemRemoved(data.size)
            data.addAll(list.map { RankingAdapter.DataItem(count to it) })
            notifyDataSetChanged()
            isLoading = false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun fetchRankingData(dataList: List<RankingEntity>, count: Int) {
        val list = mutableListOf<RankingAdapter.DataItem>()

        list.apply {
            add(RankingAdapter.DataItem("타이틀"))
            addAll(dataList.map { RankingAdapter.DataItem(count to it) })
        }

        (binding?.recyclerView?.adapter as RankingAdapter).apply {
            data = list
            notifyDataSetChanged()
        }
    }

    override fun showLoadingIndicator() {
        binding?.progressBar?.isVisible = true
    }

    override fun dismissLoadingIndicator() {
        binding?.progressBar?.isVisible = false
    }
}