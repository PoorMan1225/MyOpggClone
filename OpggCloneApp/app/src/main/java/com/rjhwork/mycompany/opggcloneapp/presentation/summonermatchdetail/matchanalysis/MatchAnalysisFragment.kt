package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.rjhwork.mycompany.opggcloneapp.data.entity.match.Match
import com.rjhwork.mycompany.opggcloneapp.databinding.FragmentMatchAnalysisBinding
import org.koin.android.scope.ScopeFragment

class MatchAnalysisFragment: ScopeFragment(), MatchAnalysisContract.View {

    override var matchData: Match? = null

    private var binding: FragmentMatchAnalysisBinding? = null

    override val presenter: MatchAnalysisContract.Presenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMatchAnalysisBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViews()
    }

    private fun initViews() {
        binding?.viewPager?.adapter = MatchAnalysisAdapter(this).apply {
            matchData?.let {
                match = it
            }
        }
    }

    private fun bindViews() {

    }

    override fun showLoadingIndicator() {
        binding?.progressBar?.isVisible = true
    }

    override fun dismissLoadingIndicator() {
        binding?.progressBar?.isVisible = false
    }

    companion object {
        const val TAG = "MatchAnalysisFragment"
    }
}