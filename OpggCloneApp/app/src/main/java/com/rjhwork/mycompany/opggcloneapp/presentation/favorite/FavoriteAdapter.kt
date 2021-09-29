package com.rjhwork.mycompany.opggcloneapp.presentation.favorite

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.mapper.toRankDrawable
import com.rjhwork.mycompany.opggcloneapp.data.preference.SharedPreferenceManager
import com.rjhwork.mycompany.opggcloneapp.data.preference.SharedPreferenceManager.Companion.SUMMONER_PROFILE_KEY
import com.rjhwork.mycompany.opggcloneapp.databinding.FavoriteItemBinding
import com.rjhwork.mycompany.opggcloneapp.extension.load
import com.rjhwork.mycompany.opggcloneapp.extension.loadCircle

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var data = emptyList<FavoriteEntity>()

    lateinit var favoriteClickListener: (FavoriteEntity) -> Unit
    lateinit var deleteClickListener: (FavoriteEntity) -> Unit
    var buttonEventCallBack: (() -> Int)? = null
    lateinit var rootClickListener: (Pair<FavoriteEntity, String>) -> Unit

    inner class ViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favorite: FavoriteEntity) {
            if (favorite.profileRank?.rank == null) {
                binding.tierImageView.setImageDrawable(
                    getDrawable(
                        binding.root.context,
                        R.drawable.empty_tier
                    )
                )
            } else {
                binding.tierImageView.setImageDrawable(
                    favorite.profileRank.rank.toRankDrawable(
                        binding.root.context
                    )
                )
            }

            binding.summonerProfileImageView.loadCircle(favorite.summonerIcon)
            binding.summonerNameTextView.text = favorite.summonerName
            binding.tierTextView.text = favorite.profileRank?.tier
            bindFavoriteImage(favorite)

            // event
            binding.favoriteImageView.setOnClickListener {
                favoriteClickListener.invoke(favorite)
            }
            binding.deleteImageView.setOnClickListener {
                deleteClickListener.invoke(favorite)
            }

            buttonEventCallBack?.invoke()?.let {
                if(it == DOWN_UP_ANIMATION) {
                    setAnimation(binding.root)
                }
            }
            binding.root.setOnClickListener {
                if(binding.favoriteImageView.visibility == View.GONE) {
                    rootClickListener.invoke(favorite to "null")
                }else {
                    rootClickListener.invoke(favorite to if(favorite.isFavorite) "true" else "false")
                }
            }
        }

        private fun bindFavoriteImage(favorite: FavoriteEntity) {
            val sp = binding.root.context.getSharedPreferences("preference", Activity.MODE_PRIVATE)
            SharedPreferenceManager(sp).apply {
                val name = getSummonerProfile(SUMMONER_PROFILE_KEY)?.name
                if (name == favorite.summonerName) {
                    binding.favoriteImageView.isVisible = false
                } else {
                    binding.favoriteImageView.isVisible = true
                    binding.favoriteImageView.setImageDrawable(
                        if (favorite.isFavorite)
                            getDrawable(binding.root.context, R.drawable.ic_baseline_star_24)
                        else
                            getDrawable(binding.root.context, R.drawable.ic_baseline_star_border_24)
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("Recycle")
    private fun setAnimation(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "y", view.bottom.toFloat(), view.top.toFloat())
        animator.duration = 200
        animator.start()
    }

    private fun getDrawable(context: Context, value: Int): Drawable? {
        return ResourcesCompat.getDrawable(
            context.resources,
            value, null
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FavoriteItemBinding.inflate(
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

    companion object {
        const val DEFAULT_ANIMATION = 1
        const val DOWN_UP_ANIMATION = 2
    }
}