package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Layout
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.databinding.ItemMostDataBinding
import com.rjhwork.mycompany.opggcloneapp.databinding.LoadingItemBinding
import com.rjhwork.mycompany.opggcloneapp.databinding.MatchLoseItemBinding
import com.rjhwork.mycompany.opggcloneapp.databinding.MatchWinItemBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.*
import com.rjhwork.mycompany.opggcloneapp.extension.load
import com.rjhwork.mycompany.opggcloneapp.extension.loadCircle
import java.lang.RuntimeException
import kotlin.math.roundToInt

@SuppressLint("SetTextI18n")
class SummonerMatchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var detailDataCallback: (PassData) -> Unit

    var data = mutableListOf<Any?>()

    inner class MatchWinViewHolder(private val binding: MatchWinItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(bindMatchModel: Any?) {
            (bindMatchModel as BindMatchModel)

            binding.championImageView.loadCircle(bindMatchModel.championIcon)
            binding.spellFirstImageView.load(bindMatchModel.spell1Icon)
            binding.spellSecondImageView.load(bindMatchModel.spell2Icon)
            binding.roonFirstImageView.loadCircle(bindMatchModel.rune1Icon)
            binding.roonSecondImageView.loadCircle(bindMatchModel.rune2Icon)
            binding.itemImageView1.load(bindMatchModel.items.item0)
            binding.itemImageView2.load(bindMatchModel.items.item1)
            binding.itemImageView3.load(bindMatchModel.items.item2)
            binding.itemImageView4.load(bindMatchModel.items.item3)
            binding.itemImageView5.load(bindMatchModel.items.item4)
            binding.itemImageView6.load(bindMatchModel.items.item5)
            binding.lastItemImageView.loadCircle(bindMatchModel.items.item6)
            // kill death
            binding.killTextView.text = bindMatchModel.killDeathAssist.kill.toString()
            binding.deathTextView.text = bindMatchModel.killDeathAssist.death.toString()
            binding.assistTextView.text = bindMatchModel.killDeathAssist.assist.toString()
            binding.killRateTextView.text =
                "킬관여 : ${bindMatchModel.killDeathAssist.killAssistRate}%"

            val killScore = getConsecutiveKill(bindMatchModel.killDeathAssist)
            if (killScore == null) {
                binding.killCountTextView.isVisible = false
            } else {
                binding.killCountTextView.isVisible = true
                binding.killCountTextView.text = killScore
            }
            // 게임 정보
            binding.gameModeTextView.text = bindMatchModel.gameMode
            binding.matchTimeTextView.text = bindMatchModel.matchDuration
            binding.timeLeftTextView.text = bindMatchModel.date

            // event
            binding.rootLayout.setOnClickListener {
                detailDataCallback.invoke(
                    PassData(
                        bindMatchModel.matchId,
                        bindMatchModel.puuid,
                        true
                    )
                )
            }
        }
    }

    inner class MatchLoseViewHolder(private val binding: MatchLoseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(bindMatchModel: Any?) {
            (bindMatchModel as BindMatchModel)
            binding.championImageView.loadCircle(bindMatchModel.championIcon)
            binding.spellFirstImageView.load(bindMatchModel.spell1Icon)
            binding.spellSecondImageView.load(bindMatchModel.spell2Icon)
            binding.roonFirstImageView.loadCircle(bindMatchModel.rune1Icon)
            binding.roonSecondImageView.loadCircle(bindMatchModel.rune2Icon)
            binding.itemImageView1.load(bindMatchModel.items.item0)
            binding.itemImageView2.load(bindMatchModel.items.item1)
            binding.itemImageView3.load(bindMatchModel.items.item2)
            binding.itemImageView4.load(bindMatchModel.items.item3)
            binding.itemImageView5.load(bindMatchModel.items.item4)
            binding.itemImageView6.load(bindMatchModel.items.item5)
            binding.lastItemImageView.loadCircle(bindMatchModel.items.item6)
            // kill death
            binding.killTextView.text = bindMatchModel.killDeathAssist.kill.toString()
            binding.deathTextView.text = bindMatchModel.killDeathAssist.death.toString()
            binding.assistTextView.text = bindMatchModel.killDeathAssist.assist.toString()
            binding.killRateTextView.text =
                "킬관여 : ${bindMatchModel.killDeathAssist.killAssistRate}%"

            val killScore = getConsecutiveKill(bindMatchModel.killDeathAssist)
            if (killScore == null) {
                binding.killCountTextView.isVisible = false
            } else {
                binding.killCountTextView.isVisible = true
                binding.killCountTextView.text = killScore
            }
            // 게임 정보
            binding.gameModeTextView.text = bindMatchModel.gameMode
            binding.matchTimeTextView.text = bindMatchModel.matchDuration
            binding.timeLeftTextView.text = bindMatchModel.date

            // event
            binding.rootLayout.setOnClickListener {
                detailDataCallback.invoke(
                    PassData(
                        bindMatchModel.matchId,
                        bindMatchModel.puuid,
                        false
                    )
                )
            }
        }
    }

    inner class HeaderViewHolder(private val binding: ItemMostDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pair: Any?) {
            (pair as Pair<*, *>)

            pair.first.let { kdaModel ->
                (kdaModel as RecentAllKdaModel)
                binding.winLoseTextView.text = "${kdaModel.win}승 ${kdaModel.lose}패"
                binding.killDeathAssistRateTextView.text = setDeathTextColor(kdaModel)
                binding.kdaTextView.text = setKdaTextColor(kdaModel, binding.root.context)
                binding.killInvolvementTextView.text = "(${(kdaModel.killAssistRate / 10)}%)"
            }

            pair.second?.let { model ->
                (model as ProfileMostChampionModel)
                model.most1?.let { most1 ->
                    binding.mostChampion1ImageView.loadCircle(most1.championIcon)
                    binding.mostChampion1WinRateTextView.text = setRateTextColor(most1)
                }

                model.most2?.let { most2 ->
                    binding.mostChampion2ImageView.loadCircle(most2.championIcon)
                    binding.mostChampion2WinRateTextView.text = setRateTextColor(most2)
                } ?: kotlin.run {
                    binding.mostChampion2ImageView.isVisible = false
                    binding.mostChampion2WinRateTextView.isVisible = false
                }
            }
        }
    }

    inner class LoadingViewHolder(private val binding: LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.progressBar.isVisible = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_HEADER -> {
                HeaderViewHolder(
                    ItemMostDataBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            WIN_MATCH_DATA -> {
                MatchWinViewHolder(
                    MatchWinItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            LOSE_MATCH_DATA -> {
                MatchLoseViewHolder(
                    MatchLoseItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_LOADING -> {
                LoadingViewHolder(
                    LoadingItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw RuntimeException("알 수 없는 ViewType 입니다.")
        }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data[position]?.let { position ->
            when (holder) {
                is HeaderViewHolder -> {
                    holder.bind(position)
                }
                is MatchWinViewHolder -> {
                    holder.bind(position)
                }
                is MatchLoseViewHolder -> {
                    holder.bind(position)
                }
                else -> throw Exception("뷰홀더 캐스팅 Exception")
            }
        } ?: kotlin.run {
            (holder as LoadingViewHolder).bind()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        // position 이 0일때 HEADER 를 넣으면 된다.
        if (position == 0) {
            return VIEW_TYPE_HEADER
        }

        if (data[position] == null) {
            return VIEW_TYPE_LOADING
        }

        return if ((data[position] as BindMatchModel).win == true)
            WIN_MATCH_DATA
        else
            LOSE_MATCH_DATA
    }

    private fun getRate(kdaModel: RecentAllKdaModel): Float {
        if (kdaModel.deaths <= 0) {
            return 100f
        }

        return kdaModel.let {
            ((((it.kills + it.assists) / it.deaths.toFloat()) * 100).toInt()) / 100f
        }
    }

    private fun setDeathTextColor(kdaModel: RecentAllKdaModel): Spannable {
        val textStyle =
            "${(kdaModel.kills) / 10f} / ${(kdaModel.deaths) / 10f} / ${(kdaModel.assists) / 10f}".toSpannable()
        val start = "${(kdaModel.kills / 10f)}".length + 2
        val end = start + "${kdaModel.deaths / 10f}".length + 1
        textStyle[start..end] = ForegroundColorSpan(Color.RED)
        return textStyle
    }

    private fun setRateTextColor(most: ProfileChampionModel): Spannable {
        val text = "${most.winRate.roundToInt()}%".toSpannable()
        if (most.winRate.roundToInt() > 60) {
            text[0..text.length] = ForegroundColorSpan(Color.RED)
        } else {
            text[0..text.length] = ForegroundColorSpan(Color.BLACK)
        }
        return text
    }

    private fun setKdaTextColor(kdaModel: RecentAllKdaModel, context: Context): Spannable {
        val rate = getRate(kdaModel)
        val text = if (rate != 100f) "${rate}:1".toSpannable() else "Perfect".toSpannable()
        when (rate) {
            in 0.0..3.0 -> text[0..text.length] = ForegroundColorSpan(
                ContextCompat.getColor(context, R.color.white_gray_3)
            )
            in 3.0..4.0 -> text[0..text.length] = ForegroundColorSpan(Color.GREEN)
            in 4.0..5.0 -> text[0..text.length] = ForegroundColorSpan(Color.BLUE)
            else -> text[0..text.length] = ForegroundColorSpan(
                ContextCompat.getColor(context, R.color.orange)
            )
        }
        return text
    }

    private fun getConsecutiveKill(killDeathAssist: KillDeathAssist): String? {
        return if (killDeathAssist.pentaKills != null && killDeathAssist.pentaKills > 0) {
            "펜타킬"
        } else if (killDeathAssist.quadraKills != null && killDeathAssist.quadraKills > 0) {
            "쿼드라킬"
        } else if (killDeathAssist.tripleKills != null && killDeathAssist.tripleKills > 0) {
            "트리플킬"
        } else if (killDeathAssist.doubleKills != null && killDeathAssist.doubleKills > 0) {
            "더블킬"
        } else {
            null
        }
    }

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val WIN_MATCH_DATA = 1
        const val LOSE_MATCH_DATA = 2
        const val VIEW_TYPE_LOADING = 3
    }
}