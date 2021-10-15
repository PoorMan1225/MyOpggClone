package com.rjhwork.mycompany.opggcloneapp.presentation.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.data.entity.favorite.FavoriteEntity
import com.rjhwork.mycompany.opggcloneapp.data.preference.SharedPreferenceManager
import com.rjhwork.mycompany.opggcloneapp.data.preference.SharedPreferenceManager.Companion.SUMMONER_PROFILE_KEY
import com.rjhwork.mycompany.opggcloneapp.databinding.FragmentSearchBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.BindProfileModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileMostChampionModel
import com.rjhwork.mycompany.opggcloneapp.domain.model.ProfileRank
import com.rjhwork.mycompany.opggcloneapp.extension.load
import com.rjhwork.mycompany.opggcloneapp.extension.loadCircle
import com.rjhwork.mycompany.opggcloneapp.presentation.MainActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.addsummoner.AddSummonerActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.favorite.FavoritesActivity
import com.rjhwork.mycompany.opggcloneapp.presentation.summonermatch.SummonerMatchActivity
import com.rjhwork.mycompany.opggcloneapp.util.Flag
import org.koin.android.scope.ScopeFragment
import kotlin.math.roundToInt

class SearchFragment : ScopeFragment(), SearchContract.View {

    private var binding: FragmentSearchBinding? = null

    override val presenter: SearchContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
        presenter.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        presenter.retry(Flag.dataCheck)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun initViews() {
        (context as MainActivity).findViewById<TextView>(R.id.toolbarTitleTextView).apply {
            text = "리그오브레전드"
        }
        binding?.favoriteRecyclerView?.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = SearchAdapter()
        }
    }

    private fun bindViews() {
        binding?.addSummonerLayout?.setOnClickListener {
            startActivity(Intent(context, AddSummonerActivity::class.java))
            (context as MainActivity).overridePendingTransition(R.anim.sliding_up, R.anim.stay)
        }
        binding?.deleteProfileImageView?.setOnClickListener {
            showDialog()
        }
        binding?.searchTextView?.setOnClickListener {
            Flag.dataCheck = false
            startActivity(Intent(context, FavoritesActivity::class.java))
            (context as MainActivity).overridePendingTransition(R.anim.sliding_up, R.anim.stay)
        }
        (binding?.favoriteRecyclerView?.adapter as SearchAdapter).favoriteClickListener = { favorite ->
            startMatchActivityWithAnimation(
                favoriteEntity = favorite,
                value = favorite.isFavorite?.let {
                    if (it) "true" else "false"
                }
            )
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("즐겨찾기에서 삭제하시겠습니까?")
            .setNegativeButton("취소") { _, _ -> }
            .setPositiveButton("완료") { _, _ ->
                val sp = requireActivity().getSharedPreferences("preference", Activity.MODE_PRIVATE)
                SharedPreferenceManager(sp).putSummonerProfile(SUMMONER_PROFILE_KEY, null)
                deleteAddSummonerProfile()
                touchEnabledTrue()
            }
            .setCancelable(false)
            .show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showFavoriteDataList(favoriteList: List<FavoriteEntity>) {
        (binding?.favoriteRecyclerView?.adapter as SearchAdapter).apply {
            data = favoriteList
            notifyDataSetChanged()
        }
    }

    override fun touchEnabledFalse() {
        binding?.addSummonerLayout?.isClickable = false
    }

    override fun touchEnabledTrue() {
        binding?.addSummonerLayout?.isClickable = true
    }

    override fun showEmptyRankLayout(profileRank: ProfileRank) {
        binding?.emptyTierRankLayout?.isVisible = profileRank.rank == null
        binding?.summonerTierLayout?.isVisible = profileRank.rank != null
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoadingIndicator() {
        binding?.progressBar?.isVisible = true
    }

    override fun dismissLoadingIndicator() {
        binding?.progressBar?.isVisible = false
    }

    override fun showFavoriteLoadingIndicator() {
        binding?.favoriteProgressBar?.isVisible = true
    }

    override fun dismissFavoriteLoadingIndicator() {
        binding?.favoriteProgressBar?.isVisible = false
    }

    override fun showEmptyFavoriteLayout() {
        binding?.emptyFavoriteListLayout?.isVisible = true
        binding?.favoriteRecyclerView?.isVisible = false
    }

    override fun showFavoriteDataLayout() {
        binding?.emptyFavoriteListLayout?.isVisible = false
        binding?.favoriteRecyclerView?.isVisible = true
    }

    override fun showEmptyChampionDataLayout(profileMostChampionModel: ProfileMostChampionModel?) {
        binding?.emptyChampionDataLayout?.isVisible = profileMostChampionModel == null
        binding?.summonerChampionDataLayout?.isVisible = profileMostChampionModel != null
    }

    override fun startMatchActivityWithAnimation(favoriteEntity: FavoriteEntity, value: String?) {
        startActivity(SummonerMatchActivity.newIntent(requireContext(), favoriteEntity, value))
        (context as MainActivity).overridePendingTransition(
            R.anim.sliding_left_and_fade_out,
            R.anim.sliding_left_and_fade_out_stay
        )
    }

    @SuppressLint("SetTextI18n")
    override fun showAddSummonerProfile(prfile: BindProfileModel) {
        binding?.profileCardView?.visibility = View.VISIBLE

        binding?.let {
            it.summonerIconImageView.loadCircle(prfile.summonerIcon)
            it.summonerNameTextView.text = prfile.summonerName
            it.summonerTierImageView.setImageDrawable(prfile.profileRank?.tier)
            it.summonerTierTextView.text = prfile.profileRank?.rank
            it.summonerTierLpTextView.text = prfile.profileRank?.leaguePoints
            it.summonerLevelTextView.text = prfile.summonerLevel.toString()

            prfile.summonerMostChampionModel?.let { model ->
                it.summonerChampionMost1ImageView.loadCircle(model.most1?.championIcon)
                it.summonerChampionMost1KdaTextView.text =
                    model.most1?.let { most1 -> "${most1.kda}:1" } ?: ""
                it.summonerChampionMost1WinRateTextView.text =
                    model.most1?.let { most1 -> "${most1.winRate.roundToInt()}%" } ?: ""
                it.summonerChampionMost2ImageView.loadCircle(model.most2?.championIcon)
                it.summonerChampionMost2KdaTextView.text =
                    model.most2?.let { most2 -> "${most2.kda}:1" } ?: ""
                it.summonerChampionMost2WinRateTextView.text =
                    model.most2?.let { most2 -> "${most2.winRate.roundToInt()}%" } ?: ""
                it.summonerChampionMost3ImageView.loadCircle(model.most3?.championIcon)
                it.summonerChampionMost3KdaTextView.text =
                    model.most3?.let { most3 -> "${most3.kda}:1" } ?: ""
                it.summonerChampionMost3WinRateTextView.text =
                    model.most3?.let { most3 -> "${most3.winRate.roundToInt()}%" } ?: ""
            }
        }

        binding?.profileDataLayout?.setOnClickListener {
            presenter.getFavoriteBySummonerName(prfile.summonerName!!)
        }
    }

    override fun deleteAddSummonerProfile() {
        binding?.profileCardView?.visibility = View.GONE
    }
}