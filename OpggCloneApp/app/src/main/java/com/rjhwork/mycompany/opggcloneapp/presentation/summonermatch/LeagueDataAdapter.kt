package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toRankDrawable
import com.rjhwork.mycompany.opggcloneapp.databinding.ItemFlexRankBinding
import com.rjhwork.mycompany.opggcloneapp.databinding.ItemSoloRankBinding
import kotlin.math.roundToInt

class LeagueDataAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = emptyList<ProfileLeagueItem?>()

    inner class SoloRankViewHolder(private val binding: ItemSoloRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(leagueItem: ProfileLeagueItem?) {
            leagueItem?.let {
                binding.summonerRankIconImageView.setImageDrawable(
                    leagueItem.tier?.toRankDrawable(
                        binding.root.context
                    )
                )
                binding.rankTextView.text = "${leagueItem.tier}"
                binding.lpTextView.text = "${leagueItem.leaguePoints}"
                binding.winLoseTextView.text = "${leagueItem.wins}승 ${leagueItem.losses}패 (${getWinLostRate(leagueItem)}%)"
            } ?: kotlin.run {
                binding.summonerRankIconImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        binding.root.context.resources,
                        R.drawable.empty_tier,
                        null
                    )
                )
                binding.rankTextView.text = "UNRANKED"
                binding.lpTextView.text = "-LP"
                binding.winLoseTextView.isVisible = false
            }
        }
    }

    private fun getWinLostRate(leagueItem: ProfileLeagueItem?): Int? {
        return leagueItem?.let {
            if (it.wins != null && it.losses != null) {
                ((it.wins / (it.wins + it.losses).toFloat()) * 100).roundToInt()
            } else {
                null
            }
        }
    }

    inner class FlexRankViewHolder(private val binding: ItemFlexRankBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(leagueItem: ProfileLeagueItem?) {
            leagueItem?.let {
                binding.summonerRankIconImageView.setImageDrawable(
                    leagueItem.tier?.toRankDrawable(
                        binding.root.context
                    )
                )
                binding.rankTextView.text = "${leagueItem.tier}"
                binding.lpTextView.text = "${leagueItem.leaguePoints}"
                binding.winLoseTextView.text = "${leagueItem.wins}승 ${leagueItem.losses}패 (${getWinLostRate(leagueItem)}%)"
            } ?: kotlin.run {
                binding.summonerRankIconImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        binding.root.context.resources,
                        R.drawable.empty_tier,
                        null
                    )
                )
                binding.rankTextView.text = "UNRANKED"
                binding.lpTextView.text = "-LP"
                binding.winLoseTextView.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            SoloRankViewHolder(
                ItemSoloRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            FlexRankViewHolder(
                ItemFlexRankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SoloRankViewHolder -> {
                holder.bind(data[position])
            }
            is FlexRankViewHolder -> {
                holder.bind(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
}