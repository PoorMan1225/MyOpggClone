package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.databinding.LayoutMatchDetailItemBinding
import com.rjhwork.mycompany.opggcloneapp.databinding.LayoutMatchDetailTotalItemBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindDetailModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindDetailTotalModel
import com.rjhwork.mycompany.opggcloneapp.extension.load
import com.rjhwork.mycompany.opggcloneapp.extension.loadCircle
import java.text.DecimalFormat

class SummonerMatchDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = emptyList<Any?>()
    var winFlag: Boolean = false
    lateinit var myPuuid: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0, 6 -> {
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
        when (holder) {
            is MatchDetailItemViewHolder -> {
                holder.bind(data[position])
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
                            if (flag) R.drawable.icon_baron_b else R.drawable.icon_baron_r
                        )
                    )
                    binding.towerImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            if (flag) R.drawable.icon_tower_b else R.drawable.icon_towe_r
                        )
                    )
                    binding.dragonImageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            if (flag) R.drawable.icon_dragon_b else R.drawable.icon_dragon_r
                        )
                    )
                }
                binding.killTextView.text = data.totalKill.toString()
                binding.deathTextView.text = data.totalDeath.toString()
                binding.assistTextView.text = data.totalAssist.toString()
                binding.towerKillCountTextView.text = data.towerKill.toString()
                binding.baronKillCountTextView.text = data.baronKill.toString()
                binding.dragonKillCountTextView.text = data.dragonKill.toString()
                binding.winTextView.setWinOrLoseText(data.winFlag)
                binding.teamTextView.text = getTeamText(data.teamType)
            }
        }
    }

    private fun getTeamText(teamType: Int?): String {
        return teamType?.let { idx ->
            if (idx == 100) "(블루)" else "(레드)"
        } ?: ""
    }

    private fun TextView.setWinOrLoseText(winFlag: Boolean?) {
        winFlag?.let { win ->
            setTextColor(
                ContextCompat.getColor(context,
                    if (win)
                        R.color.win_background
                    else
                        R.color.lose_background
                )
            )
            text = if(win) "승리" else "패배"
        }
    }

    inner class MatchDetailItemViewHolder(private val binding: LayoutMatchDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Any?) {
            if (data is BindDetailModel) {
                binding.championImageView.loadCircle(data.championIcon)
                binding.championLevelTextView.text = data.championLevel.toString()
                binding.spellFirstImageView.load(data.spell1)
                binding.spellSecondImageView.load(data.spell2)
                binding.roonFirstImageView.loadCircle(data.rune1)
                binding.roonSecondImageView.load(data.rune2)
                binding.nameTextView.text = data.summonerName
                binding.killTextView.text = data.kill.toString()
                binding.deathTextView.text = data.death.toString()
                binding.assistTextView.text = data.assist.toString()
                binding.kdaTextView.text = getTextColor(data.kda, binding.root.context)
                binding.tierBadgeTextView.setBadgeText(data.tier)
                // 아이탬 이미지 바인딩
                bindItems(data, binding)
                binding.csGoldTextView.text = "${data.cs}(${data.minuteCs}) / ${data.earnedGold}"
                binding.progressLayout.dealTextView.text =
                    DecimalFormat("###,###,###").format(data.damage)
                binding.progressLayout.progressBar.progress = getProgressRate(data)

                // 내 전적인지 확인.
                binding.myCheckView.isVisible = data.puuid == myPuuid
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        if (data.puuid == myPuuid)
                            R.color.light_green
                        else
                            R.color.white
                    )
                )
            }
        }
    }

    private fun getProgressRate(data: BindDetailModel): Int {
        val deal = data.damage
        val maxDeal = data.maxDamage

        return (deal * 100) / maxDeal
    }

    private fun bindItems(data: BindDetailModel, binding: LayoutMatchDetailItemBinding) {
        binding.itemImageView1.load(data.items.item0)
        binding.itemImageView2.load(data.items.item1)
        binding.itemImageView3.load(data.items.item2)
        binding.itemImageView4.load(data.items.item3)
        binding.itemImageView5.load(data.items.item4)
        binding.itemImageView6.load(data.items.item5)
        binding.lastItemImageView.loadCircle(data.items.item6)
    }

    private fun getTextColor(kda: String, context: Context): Spannable {
        val text: Spannable = if (kda == "Perfect") {
            kda.toSpannable()
        } else {
            "${kda.toFloat()}:1".toSpannable()
        }

        when (kda) {
            "Perfect" -> {
                text[0..text.length] =
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.orange))
            }
            else -> {
                when (kda.toFloat()) {
                    in 0.0..3.0 -> text[0..text.length] =
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.darker_gray))
                    in 3.0..4.0 -> text[0..text.length] =
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.green))
                    in 4.0..5.0 -> text[0..text.length] =
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.win_background))
                    else -> text[0..text.length] =
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.orange))
                }
            }
        }
        return text
    }
}