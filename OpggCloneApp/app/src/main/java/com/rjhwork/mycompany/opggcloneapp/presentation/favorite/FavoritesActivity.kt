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
        (binding.recyclerView.adapter as FavoriteAdapter).favoriteClickListener =
            { favoriteEntity ->
                (binding.recyclerView.adapter as FavoriteAdapter).buttonEventCallBack =
                    { DEFAULT_ANIMATION }
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
            .setTitle("'${name}' 소환사가 존재하지 않습니다.")
            .setPositiveButton("완료") { _, _ ->

            }
            .setCancelable(false)
            // 다이얼로그 취소 금지. 다른데 누르거나 백프래스 눌러도 안꺼짐
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
                if (favoriteEntity.isFavorite) "true" else "false"
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
        if (favoriteEntity.isFavorite) {
            AlertDialog.Builder(this)
                .setTitle("즐겨찾기에서 삭제하시겠습니까?")
                .setPositiveButton("완료") { _, _ ->
                    presenter.updateFavoriteData(favoriteEntity)
                }.setNegativeButton("취소") { _, _ -> }
                .setCancelable(false)
                // 다이얼로그 취소 금지. 다른데 누르거나 백프래스 눌러도 안꺼짐
                .show()
        } else {
            presenter.updateFavoriteData(favoriteEntity)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.stay, R.anim.sliding_down)
    }
}