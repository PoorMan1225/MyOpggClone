package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.analysisfragment

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.databinding.AnalysisDataItemBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindAnalysisData
import com.rjhwork.mycompany.opggcloneapp.extension.loadBoarderCircularImage
import com.rjhwork.mycompany.opggcloneapp.extension.loadCircle
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi

class AnalysisDataAdapter :
    RecyclerView.Adapter<AnalysisDataAdapter.ViewHolder>() {

    var dataList:List<BindAnalysisData>? = null
    var puuid: String? = null
    var fragmentPosition: Int = -1

    inner class ViewHolder(val binding: AnalysisDataItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: BindAnalysisData) {
            when (adapterPosition) {
                0, 1, 2 -> {
                    setMyBackgroundColor(adapterPosition)
                    binding.rankingBadgeTextView.isVisible = true
                    binding.rankingBadgeTextView.setBadgeTextColor(
                        when (adapterPosition) {
                            0 -> "1st"
                            1 -> "2nd"
                            2 -> "3rd"
                            else -> ""
                        },
                        getRankingColor()
                    )
                    binding.championImageView.loadBoarderCircularImage(
                        data.championName?.let { DataDragonApi.getChampionIconUrl(it) },
                        4f,
                        getRankingColor()
                    )
                    setProgressData(adapterPosition)
                }
                else -> {
                    setMyBackgroundColor(adapterPosition)
                    binding.championImageView.loadCircle(
                        data.championName?.let {
                            DataDragonApi.getChampionIconUrl(it)
                        }
                    )
                    binding.rankingBadgeTextView.isVisible = false
                    setProgressData(adapterPosition)
                }
            }
        }
    }

    private fun ViewHolder.setMyBackgroundColor(adapterPosition: Int) {
        puuid?.let {
            if(dataList?.get(adapterPosition)?.puuid == it) {
                binding.myCheckView.visibility = View.VISIBLE
                binding.itemLayout.setBackgroundColor(getColor(binding.root.context, R.color.light_green))
            }else {
                binding.myCheckView.visibility = View.INVISIBLE
                binding.itemLayout.setBackgroundColor(getColor(binding.root.context, R.color.white))
            }
        }
    }

    private fun ViewHolder.setProgressData(adapterPosition: Int) {
        when (fragmentPosition) {
            0 -> setProgressDataBranches(adapterPosition) { data -> data.kills }
            1 -> setProgressDataBranches(adapterPosition) { data -> data.goldEarned }
            2 -> setProgressDataBranches(adapterPosition) { data -> data.totalDamageDealtToChampions }
            3 -> setProgressDataBranches(adapterPosition) { data -> data.totalDamageShieldedOnTeammates }
            4 -> setProgressDataBranches(adapterPosition) { data -> data.totalMinionsKilled }
            5 -> setProgressDataBranches(adapterPosition) { data -> data.wardsPlaced }
        }
    }

    private fun ViewHolder.setProgressDataBranches(
        adapterPosition: Int,
        condition: (BindAnalysisData) -> Int?
    ) {
        dataList?.let { it ->
            binding.progressBar.progressTintList = ColorStateList.valueOf(
                if (it[adapterPosition].win == true) {
                    getColor(binding.root.context, R.color.win_background)
                } else {
                    getColor(binding.root.context, R.color.lose_background)
                }
            )
            binding.progressBar.progress = (adapterPosition * 100) / (condition(it[0]) ?: 0)
        } ?: kotlin.run {
            binding.progressBar.progress = 0
        }
    }

    private fun ViewHolder.getRankingColor() =
        when (adapterPosition) {
            0 -> getColor(binding.root.context, R.color.gold_color)
            1 -> getColor(binding.root.context, R.color.silver_color)
            2 -> getColor(binding.root.context, R.color.bronze_color)
            else -> getColor(binding.root.context, R.color.white)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AnalysisDataItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int = dataList?.size ?: 0
}