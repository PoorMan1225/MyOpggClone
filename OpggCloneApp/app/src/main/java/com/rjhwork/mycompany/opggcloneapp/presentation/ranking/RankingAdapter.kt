package com.rjhwork.mycompany.opggcloneapp.presentation.ranking

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.databinding.LoadingItemBinding
import com.rjhwork.mycompany.opggcloneapp.databinding.RankingHeaderItemBinding
import com.rjhwork.mycompany.opggcloneapp.databinding.RankingItemBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.RankingModel
import java.text.DecimalFormat

class RankingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = mutableListOf<DataItem>()
    private var df = DecimalFormat("###,###,###")

    inner class RankingViewHolder(private val binding: RankingItemBinding) : RecyclerView.ViewHolder(binding.root) {
         @SuppressLint("SetTextI18n")
         fun bind(item: Any) {
             (item as RankingModel)
             binding.summonerNameTextView.text = item.summonerName
             binding.lpTextView.text = "${df.format(item.leaguePoints)}LP"
             binding.rankingTextView.text = adapterPosition.toString()

             val tierText = getTierText(item.count)
             if(tierText.isNotEmpty()) {
                 binding.tierBadgeTextView.setBadgeTextColor(tierText, R.color.master_color)
             }
         }
    }

    inner class HeaderViewHolder(private val binding: RankingHeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class LoadingViewHolder(private val binding: LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.progressBar.isVisible = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RANKING_HEADER_ITEM -> {
                HeaderViewHolder(
                    RankingHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            RANKING_ITEM -> {
                RankingViewHolder(
                    RankingItemBinding.inflate(
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
            else -> throw Exception("RankingAdapter : 올 수 없는 뷰타입")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemValue = data[position].value
        when {
            holder is HeaderViewHolder && itemValue is String -> Unit
            holder is RankingViewHolder && itemValue is RankingModel -> holder.bind(itemValue)
            holder is LoadingViewHolder && itemValue == null -> holder.bind()
            else -> throw Exception("RankingAdapter : 올 수 없는 뷰홀더")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position].value) {
            is String -> RANKING_HEADER_ITEM
            is RankingModel -> RANKING_ITEM
            else -> VIEW_TYPE_LOADING
        }
    }

    private fun getTierText(tierCount: Int): String {
        return when (tierCount) {
            1 -> "C1"
            2 -> "GM1"
            3 -> "M1"
            else -> ""
        }
    }

    data class DataItem(val value: Any?)

    companion object {
        const val RANKING_HEADER_ITEM = 0
        const val RANKING_ITEM = 1
        const val VIEW_TYPE_LOADING = 2
    }
}