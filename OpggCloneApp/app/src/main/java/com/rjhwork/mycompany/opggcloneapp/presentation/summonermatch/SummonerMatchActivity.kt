package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.entity.leaguedata.ProfileLeagueItem
import com.rjhwork.mycompany.opggcloneapp.databinding.ActivitySummonerMatchBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindMatchModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileMostChampionModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.RecentAllKdaModel
import com.rjhwork.mycompany.opggcloneapp.extension.loadCircle
import com.rjhwork.mycompany.opggcloneapp.presentation.MainActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.SummonerMatchDetailActivity
import org.koin.android.scope.ScopeActivity

class SummonerMatchActivity : ScopeActivity(), SummonerMatchContract.View {

    override val presenter: SummonerMatchContract.Presenter by inject()
    private lateinit var favoriteEntity: FavoriteEntity
    private var isLoading = false

    private var index = 0

    private val binding: ActivitySummonerMatchBinding by lazy {
        ActivitySummonerMatchBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        bindViews()
        presenter.onCreate(
            intent.getParcelableExtra(SUMMONER_PUUID_KEY),
            intent.getStringExtra(SUMMONER_FAVORITE_CHECK)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun bindViews() {
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val rate = (100 / binding.constraintLayout.height.toFloat())
            binding.scrollProfileImageView.alpha = (rate * (verticalOffset * -1)) / 100
        })

        binding.closeImageView.setOnClickListener {
            finish()
            overridePendingTransition(
                R.anim.sliding_right_and_fade_out_stay,
                R.anim.sliding_right_and_fade_out
            )
        }

        binding.refreshLayout.setOnClickListener {
            presenter.showRefreshList(favoriteEntity)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManger = (binding.recyclerView.layoutManager as LinearLayoutManager)
                if (!isLoading) {
                    if (layoutManger.findLastCompletelyVisibleItemPosition()
                        == ((binding.recyclerView.adapter as SummonerMatchAdapter).data.size - 1)
                    ) {
                        showViewHolderProgress()
                        index += 10
                        if (presenter.showMoreList(favoriteEntity, index) == -1) {
                            index -= 10
                        }
                        isLoading = true
                    }
                }
            }
        })

        (binding.recyclerView.adapter as SummonerMatchAdapter).detailDataCallback = { passData ->
            startActivity(
                SummonerMatchDetailActivity.newIntent(
                    this@SummonerMatchActivity,
                    passData
                )
            )
            overridePendingTransition(
                R.anim.sliding_left_and_fade_out,
                R.anim.sliding_left_and_fade_out_stay
            )
        }
    }


    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@SummonerMatchActivity, RecyclerView.VERTICAL, false)
            adapter = SummonerMatchAdapter()
        }

        binding.rankRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@SummonerMatchActivity, RecyclerView.HORIZONTAL, false)
            adapter = LeagueDataAdapter()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val offset = (binding.toolbar.width / 2f) - (binding.scrollProfileImageView.width / 2f)
        binding.scrollProfileImageView.x = offset
    }

    override fun showLoadingIndicator() {
        binding.progressBar.isVisible = true
    }

    override fun dismissLoadingIndicator() {
        binding.progressBar.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showListData(
        pairList: Pair<List<BindMatchModel?>, RecentAllKdaModel>,
        matchMostData: ProfileMostChampionModel?
    ) {
        val dataList = mutableListOf<Any?>()
        dataList.apply {
            add(pairList.second to matchMostData)
            addAll(pairList.first.toMutableList())
        }

        (binding.recyclerView.adapter as SummonerMatchAdapter).apply {
            data = dataList
            notifyDataSetChanged()
        }
    }

    override fun showProfile(favoriteEntity: FavoriteEntity, value: String?) {
        this@SummonerMatchActivity.favoriteEntity = favoriteEntity
        binding.profileIconImageView.loadCircle(favoriteEntity.summonerIcon)
        binding.scrollProfileImageView.loadCircle(favoriteEntity.summonerIcon)
        binding.closeImageView.isVisible = true
        binding.profileNameTextView.text = favoriteEntity.summonerName
        binding.profileLevelTextView.background =
            ResourcesCompat.getDrawable(resources, R.drawable.shape_rectangle_corner_black, null)
        binding.profileLevelTextView.text = favoriteEntity.summonerLevel
        binding.favoriteImageView.visibility = if(value == null) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
        binding.refreshLayout.isVisible = true
        setFavoriteImageView(value)

        binding.favoriteImageView.setOnClickListener {
            showFavoriteDeleteDialog(this@SummonerMatchActivity.favoriteEntity)
        }
    }

    override fun setFavoriteImageView(value: String?) {
        if(value == null) return
        binding.favoriteImageView.setImageDrawable(
            when (value) {
                "true" -> ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_star_24,
                    null
                )
                "false" -> ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_star_border_24,
                    null
                )
                else -> null
            }
        )
    }

    override fun showViewHolderProgress() {
        (binding.recyclerView.adapter as SummonerMatchAdapter).apply {
            data.add(null)
            notifyItemInserted(data.size - 1)
        }
        isLoading = true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun addListData(pairList: Pair<List<BindMatchModel?>, RecentAllKdaModel>) {
        (binding.recyclerView.adapter as SummonerMatchAdapter).apply {
            data.removeAt(data.size - 1)
            notifyItemRemoved(data.size)
            data.addAll(pairList.first.toMutableList())
            notifyDataSetChanged()
            isLoading = false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showLeagueData(summonerLeagueData: List<ProfileLeagueItem?>?) {
        val summonerLeagueList: MutableList<ProfileLeagueItem?> = mutableListOf()
        summonerLeagueData?.let { list ->
            summonerLeagueList.addAll(list)

            if (summonerLeagueList.size < 2) {
                summonerLeagueList.add(null)
            }
        } ?: kotlin.run {
            summonerLeagueList.apply {
                add(null)
                add(null)
            }
        }
        (binding.rankRecyclerView.adapter as LeagueDataAdapter).apply {
            data = summonerLeagueList
            notifyDataSetChanged()
        }
    }

    override fun showRefreshProgress() {
        binding.refreshTextView.isVisible = false
        binding.refreshProgressBar.isVisible = true
    }

    override fun dismissRefreshProgress() {
        binding.refreshTextView.isVisible = true
        binding.refreshProgressBar.isVisible = false
    }

    override fun enableRefreshClick() {
        binding.refreshLayout.isClickable = true
    }

    override fun disableRefreshClick() {
        binding.refreshLayout.isClickable = false
    }

    override fun showTimeLimitDialog(timeMills: Long, currentMills: Long) {
        val leftTimeMills = ((timeMills + (60 * 2 * 1000) + 1000) - currentMills) / 1000
        val lastTimeMills = (currentMills - timeMills) / 1000

        AlertDialog.Builder(this)
            .setTitle("처음 갱신한지 ${lastTimeMills}초 지났습니다. ${leftTimeMills}초 후 다시 시도해주세요.")
            .setPositiveButton("완료") { _, _ -> }
            .setCancelable(false)
            // 다이얼로그 취소 금지. 다른데 누르거나 백프래스 눌러도 안꺼짐
            .show()
    }

    override fun showFavoriteDeleteDialog(favoriteEntity: FavoriteEntity) {
        favoriteEntity.isFavorite?.let {
            if (it) {
                AlertDialog.Builder(this)
                    .setTitle("즐겨찾기에서 삭제하시겠습니까?")
                    .setPositiveButton("완료") { _, _ ->
                        this.favoriteEntity.isFavorite = this.favoriteEntity.isFavorite?.not()
                        presenter.updateFavoriteData(this.favoriteEntity)
                    }.setNegativeButton("취소") { _, _ -> }
                    .setCancelable(false)
                    // 다이얼로그 취소 금지. 다른데 누르거나 백프래스 눌러도 안꺼짐
                    .show()
            } else {
                this.favoriteEntity.isFavorite = this.favoriteEntity.isFavorite?.not()
                presenter.updateFavoriteData(this.favoriteEntity)
                Toast.makeText(this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
        } ?: kotlin.run {
            return
        }
    }

    companion object {
        const val SUMMONER_PUUID_KEY = "SUMMONER_PUUID_KEY"
        private const val SUMMONER_FAVORITE_CHECK = "SUMMONER_FAVORITE_NULL"

        fun newIntent(context: Context, favoriteEntity: FavoriteEntity, value: String?): Intent =
            Intent(context, SummonerMatchActivity::class.java).apply {
                putExtra(SUMMONER_PUUID_KEY, favoriteEntity)
                putExtra(SUMMONER_FAVORITE_CHECK, value)
            }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(
            R.anim.sliding_right_and_fade_out_stay,
            R.anim.sliding_right_and_fade_out
        )
    }
}