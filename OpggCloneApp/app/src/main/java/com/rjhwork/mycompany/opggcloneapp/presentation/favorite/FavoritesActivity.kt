package com.rjhwork.mycompany.opggcloneapp.presentation.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.databinding.ActivityFavoritesBinding
import com.rjhwork.mycompany.opggcloneapp.presentation.MainActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.favorite.FavoriteAdapter.Companion.DEFAULT_ANIMATION
import com.rjhwork.mycompany.opggcloneapp.presentation.favorite.FavoriteAdapter.Companion.DOWN_UP_ANIMATION
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch.SummonerMatchActivity
import org.koin.android.scope.ScopeActivity

class FavoritesActivity : ScopeActivity(), FavoriteContract.View {

    override val presenter: FavoriteContract.Presenter by inject()
    private val binding: ActivityFavoritesBinding by lazy {
        ActivityFavoritesBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        bindViews()
        presenter.onCreate()
    }

    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@FavoritesActivity, RecyclerView.VERTICAL, false)
            adapter = FavoriteAdapter()
        }
    }

    private fun bindViews() {
        (binding.recyclerView.adapter as FavoriteAdapter).favoriteClickListener = { favoriteEntity ->
                (binding.recyclerView.adapter as FavoriteAdapter).buttonEventCallBack = { DEFAULT_ANIMATION }
                showFavoriteDeleteDialog(favoriteEntity)
            }

        (binding.recyclerView.adapter as FavoriteAdapter).deleteClickListener = { favoriteEntity ->
            (binding.recyclerView.adapter as FavoriteAdapter).buttonEventCallBack =
                { DOWN_UP_ANIMATION }
            presenter.deleteFavoriteData(favoriteEntity)
        }

        (binding.recyclerView.adapter as FavoriteAdapter).rootClickListener = { pair ->
            startMatchActivityWithAnimation(
                favoriteEntity = pair.first,
                value = pair.second
            )
        }

        binding.closeImageView.setOnClickListener {
            finish()
            overridePendingTransition(
                R.anim.stay,
                R.anim.sliding_down
            )
        }

        binding.searchImageView.setOnClickListener {
            val text = binding.searchEditText.text
            if (text.isNullOrBlank().not()) {
                presenter.requestFavoriteData(text.toString())
            }
        }
    }

    private fun startMatchActivityWithAnimation(favoriteEntity: FavoriteEntity, value: String?) {
        startActivity(
            SummonerMatchActivity.newIntent(
                this@FavoritesActivity,
                favoriteEntity,
                value
            )
        )
        (this@FavoritesActivity).overridePendingTransition(
            R.anim.sliding_left_and_fade_out,
            R.anim.sliding_left_and_fade_out_stay
        )
    }


    override fun showDialog(name: String) {
        AlertDialog.Builder(this)
            .setTitle("'${name}' ???????????? ???????????? ????????????.")
            .setPositiveButton("??????") { _, _ ->

            }
            .setCancelable(false)
            // ??????????????? ?????? ??????. ????????? ???????????? ???????????? ????????? ?????????
            .show()
    }

    override fun showLoadingIndicator() {
        binding.progressBar.isVisible = true
    }

    override fun dismissLoadingIndicator() {
        binding.progressBar.isVisible = false
    }

    override fun startToMatchListActivityWithAnimation(favoriteEntity: FavoriteEntity) {
        startActivity(
            SummonerMatchActivity.newIntent(
                this@FavoritesActivity,
                favoriteEntity,
                favoriteEntity.isFavorite?.let {
                    if (it) "true" else "false"
                }
            )
        )
        overridePendingTransition(
            R.anim.sliding_left_and_fade_out,
            R.anim.sliding_left_and_fade_out_stay
        )
    }

    override fun showNoDataDescription() {
        binding.emptyFavoriteDataLayout.isVisible = true
        binding.recyclerView.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showTrackingFavoriteData(items: List<FavoriteEntity>) {
        binding.emptyFavoriteDataLayout.isVisible = false
        binding.recyclerView.isVisible = true
        (binding.recyclerView.adapter as FavoriteAdapter).run {
            data = items
            notifyDataSetChanged()
        }
    }

    override fun showFavoriteDeleteDialog(favoriteEntity: FavoriteEntity) {
        favoriteEntity.isFavorite?.let {
            if (it) {
                AlertDialog.Builder(this)
                    .setTitle("?????????????????? ?????????????????????????")
                    .setPositiveButton("??????") { _, _ ->
                        presenter.updateFavoriteData(favoriteEntity)
                    }.setNegativeButton("??????") { _, _ -> }
                    .setCancelable(false)
                    // ??????????????? ?????? ??????. ????????? ???????????? ???????????? ????????? ?????????
                    .show()
            } else {
                presenter.updateFavoriteData(favoriteEntity)
            }
        } ?: kotlin.run {
            return
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay, R.anim.sliding_down)
    }
}