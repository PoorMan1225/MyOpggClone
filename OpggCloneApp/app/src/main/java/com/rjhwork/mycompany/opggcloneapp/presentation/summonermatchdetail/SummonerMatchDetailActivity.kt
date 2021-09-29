package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.data.entity.spell.Spell
import com.rjhwork.mycompany.opggcloneapp.databinding.ActivitySummonerMatchDetailBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindDetailTotalModel
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch.SummonerMatchAdapter
import org.koin.android.scope.ScopeActivity

class SummonerMatchDetailActivity : ScopeActivity(), SummonerMatchDetailContract.View {

    private val binding by lazy { ActivitySummonerMatchDetailBinding.inflate(layoutInflater) }

    override val presenter: SummonerMatchDetailContract.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()

        presenter.onCreate(
            intent.getStringExtra(MATCH_ID),
            intent.getStringExtra(SUMMONER_PUUID),
            intent.getBooleanExtra(WIN_LOSE_CHECK_FLAG, false)
        )
    }

    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@SummonerMatchDetailActivity, RecyclerView.VERTICAL, false)
            adapter = SummonerMatchDetailAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoadingIndicator() {
        binding.progressBar.isVisible = true
    }

    override fun dismissLoadingIndicator() {
        binding.progressBar.isVisible = false
    }

    override fun showAverageTextView() {
        binding.averageTierTextView.isVisible = true
    }

    override fun dismissAverageTextView() {
        binding.averageTierTextView.isVisible = false
    }

    @SuppressLint("SetTextI18n")
    override fun setTitleData(
        gameMode: String,
        gameDate: CharSequence,
        gameDuration: String,
        averageTier: String,
        winLoseFlag: Boolean
    ) {
        binding.appBar.setBackgroundColor(
            ContextCompat.getColor(
                this@SummonerMatchDetailActivity,
                if (winLoseFlag) R.color.win_background else R.color.lose_background
            )
        )
        binding.gameTypeTextView.text = gameMode
        binding.averageTierTextView.text = "평균티어 $averageTier"
        binding.dateTextView.text = gameDate
        binding.timeTextView.text = gameDuration
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showDataList(dataList: List<Any?>, puuid: String?, winLoseFlag: Boolean) {
        (binding.recyclerView.adapter as SummonerMatchDetailAdapter).apply {
            data = dataList
            winFlag = winLoseFlag
            puuid?.let {
                this.puuid = it
            }
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val MATCH_ID = "MATCH_ID"
        private const val SUMMONER_PUUID = "SUMMONER_PUUID"
        private const val WIN_LOSE_CHECK_FLAG = "WIN_LOSE_CHECK_FLAG"

        fun newIntent(context: Context, value1: String, value2: String, value3: Boolean): Intent =
            Intent(context, SummonerMatchDetailActivity::class.java).apply {
                putExtra(MATCH_ID, value1)
                putExtra(SUMMONER_PUUID, value2)
                putExtra(WIN_LOSE_CHECK_FLAG, value3)
            }
    }
}