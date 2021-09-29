package com.rjhwork.mycompany.opggcloneapp.presentation.search

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toRankDrawable
import com.rjhwork.mycompany.opggcloneapp.databinding.FavoriteRecyclerviewItemBinding
import com.rjhwork.mycompany.opggcloneapp.extension.load
import com.rjhwork.mycompany.opggcloneapp.extension.loadCircle

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var data = emptyList<FavoriteEntity>()
    lateinit var favoriteClickListener:((FavoriteEntity) -> Unit)

    inner class ViewHolder(private val binding: FavoriteRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteEntity: FavoriteEntity) {
            binding.itemConstraintLayout.setOnClickListener {
                favoriteClickListener.invoke(favoriteEntity)
            }

            binding.summonerProfileIconImageView.loadCircle(favoriteEntity.summonerIcon)
            binding.summonerProfileLevelTextView.text = favoriteEntity.summonerLevel
            binding.summonerRankTextView.text = favoriteEntity.profileRank?.tier
            binding.summonerRankImageView.setImageDrawable(
                if (favoriteEntity.profileRank?.rank == null) getDrawable(
                    binding.root.context,
                    R.drawable.empty_tier
                ) else favoriteEntity.profileRank.rank.toRankDrawable(
                    binding.root.context
                )
            )
            binding.summonerProfileTextView.text = favoriteEntity.summonerName
        }
    }

    private fun getDrawable(context: Context, value: Int): Drawable? {
        return ResourcesCompat.getDrawable(
            context.resources,
            value, null
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FavoriteRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}