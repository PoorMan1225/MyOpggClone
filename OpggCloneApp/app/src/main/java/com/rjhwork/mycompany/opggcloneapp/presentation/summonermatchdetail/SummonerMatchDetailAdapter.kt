package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Participant
import com.rjhwork.mycompany.opggcloneapp.databinding.LayoutMatchDetailItemBinding
import com.rjhwork.mycompany.opggcloneapp.databinding.LayoutMatchDetailTotalItemBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindDetailTotalModel
import com.rjhwork.mycompany.opggcloneapp.extension.loadCircle
import com.rjhwork.mycompany.opggcloneapp.util.DataDragonApi

class SummonerMatchDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = emptyList<Any?>()
    var winFlag: Boolean = false
    lateinit var puuid: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0, 1 -> {
                MatchDetailTotalViewHolder(
                    LayoutMatchDetailTotalItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                MatchDetailItemViewHolder(
                    LayoutMatchDetailItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MatchDetailItemViewHolder -> {

            }
            is MatchDetailTotalViewHolder -> {
                holder.bind(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MatchDetailTotalViewHolder(private val binding: LayoutMatchDetailTotalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Any?) {
            if (data is BindDetailTotalModel) {
                data.winFlag?.let { flag ->
                    binding.root.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            if (flag) R.color.light_win_background else R.color.light_lose_background
                        )
                    )
                    binding.baronImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            if (winFlag) R.drawable.icon_baron_b else R.drawable.icon_baron_r
                        )
                    )
                    binding.towerImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            if (winFlag) R.drawable.icon_tower_b else R.drawable.icon_towe_r
                        )
                    )
                    binding.dragonImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            if (winFlag) R.drawable.icon_dragon_b else R.drawable.icon_dragon_r
                        )
                    )
                }
                binding.killTextView.text = data.totalKill.toString()
                binding.deathTextView.text = data.totalDeath.toString()
                binding.assistTextView.text = data.totalAssist.toString()
            }
        }
    }

    inner class MatchDetailItemViewHolder(private val binding: LayoutMatchDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any?) {
            if(data is Participant)  {
                data.championName?.let { name ->
                    binding.championImageView.loadCircle(DataDragonApi.getChampionIconUrl(name))
                }
                binding.championLevelTextView.text = data.champLevel.toString()

            }
        }
    }
}