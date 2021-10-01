package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.databinding.ActivitySummonerMatchDetailBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.PassData
import org.koin.android.scope.ScopeActivity

class SummonerMatchDetailActivity : ScopeActivity(), SummonerMatchDetailContract.View {

    private val binding by lazy { ActivitySummonerMatchDetailBinding.inflate(layoutInflater) }
    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.matchDetailHostContainer) as NavHostFragment).navController
    }
    private var passData: PassData? = null

    override val presenter: SummonerMatchDetailContract.Presenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        passData = intent.getParcelableExtra(PASS_DATA_KEY)
        initViews()
        bindViews()
        presenter.onCreate(passData)

    }

    private fun bindViews() {
        binding.closeImageView.setOnClickListener {
            finish()
            overridePendingTransition(
                R.anim.sliding_right_and_fade_out_stay,
                R.anim.sliding_right_and_fade_out
            )
        }
        binding.section1TextView.setOnClickListener {
            passData?.let { data ->
                sectionOneSet(data)
            }
        }
        binding.section2TextView.setOnClickListener {
            passData?.let { data ->
                sectionTwoSet(data)
            }
        }
    }

    private fun initViews() {
        val bundle = Bundle().apply {
            putParcelable(PASS_DATA_KEY, passData)
        }
        navigationController.setGraph(navigationController.graph, bundle)
    }

    private fun sectionOneSet(data: PassData) {
        binding.section1TextView.setTextColor(getFlagColor(data.winLoseFlag))
        binding.section1TextView.setBackgroundColor(getColor(R.color.white))
        binding.section2TextView.setTextColor(getColor(R.color.white))
        binding.section2TextView.setBackgroundColor(getFlagColor(data.winLoseFlag))
    }

    private fun sectionTwoSet(data: PassData) {
        binding.section1TextView.setTextColor(getColor(R.color.white))
        binding.section1TextView.setBackgroundColor(getFlagColor(data.winLoseFlag))
        binding.section2TextView.setTextColor(getFlagColor(data.winLoseFlag))
        binding.section2TextView.setBackgroundColor(getColor(R.color.white))
    }

    private fun getFlagColor(flag: Boolean) =
        if (flag)
            getColor(R.color.win_background)
        else
            getColor(R.color.lose_background)

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
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
            if (winLoseFlag)
                ContextCompat.getColor(this, R.color.win_background)
            else
                ContextCompat.getColor(this, R.color.lose_background)
        )
        binding.winTextView.text = if (winLoseFlag) "승리" else "패배"
        binding.gameTypeTextView.text = gameMode
        binding.averageTierTextView.text = "평균티어 $averageTier"
        binding.dateTextView.text = gameDate
        binding.timeTextView.text = gameDuration
        passData?.let { sectionOneSet(it) }
    }

    companion object {
        const val PASS_DATA_KEY = "PASS_DATA_KEY"

        fun newIntent(context: Context, passData: PassData): Intent =
            Intent(context, SummonerMatchDetailActivity::class.java).apply {
                putExtra(PASS_DATA_KEY, passData)
            }
    }
}