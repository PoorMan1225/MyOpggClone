package com.rjhwork.mycompany.opggcloneapp.presentation.ranking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.data.entity.ranking.RankingEntity
import com.rjhwork.mycompany.opggcloneapp.databinding.FragmentRankingBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.RankingModel
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
        presenter.onViewCreated()
    }

    private fun initViews() {
        binding?.recyclerView?.apply {
            addItemDecoration(DividerItemDecoration(this.context, 1))
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = RankingAdapter()
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
                        presenter.showMoreRankingData()
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
    override fun addRankingList(list: List<RankingModel>?) {
        (binding?.recyclerView?.adapter as RankingAdapter).apply {
            data.removeAt(data.size - 1)
            notifyItemRemoved(data.size)
            val rankingModels = list?.map { RankingAdapter.DataItem(it) }
            rankingModels?.let { data.addAll(it) }
            notifyDataSetChanged()
            isLoading = false
        }
    }

    override fun noDataRankingList() {
        (binding?.recyclerView?.adapter as RankingAdapter).apply {
            data.removeAt(data.size - 1)
            notifyItemRemoved(data.size)
            isLoading = false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun fetchRankingData(dataList: List<RankingModel>?) {
        val list = mutableListOf<RankingAdapter.DataItem>()

        list.apply {
            add(RankingAdapter.DataItem("헤더"))
            val rankingModels = dataList?.map { RankingAdapter.DataItem(it) }
            rankingModels?.let { addAll(it) }
        }

        (binding?.recyclerView?.adapter as? RankingAdapter)?.apply {
            this.data = list
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