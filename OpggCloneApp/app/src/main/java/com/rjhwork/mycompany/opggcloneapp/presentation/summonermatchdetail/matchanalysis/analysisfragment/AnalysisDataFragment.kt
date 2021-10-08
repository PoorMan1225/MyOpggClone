package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.analysisfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rjhwork.mycompany.opggcloneapp.databinding.FragmentAnalysisDataBinding
import com.rjhwork.mycompany.opggcloneapp.domain.model.AnalysisModel

class AnalysisDataFragment() : Fragment() {

    private var binding: FragmentAnalysisDataBinding? = null
    private var analysisModel: AnalysisModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAnalysisDataBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        analysisModel = arguments?.getParcelable(PASS_ANALYSIS_DATA)

        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = AnalysisDataAdapter().apply {
                analysisModel?.let { model ->
                    binding?.winTeamAverageTextView?.text = model.sumWin.toString()
                    binding?.loseTeamAverageTextView?.text = model.sumLose.toString()
                    this.puuid = model.myPuuid
                    this.fragmentPosition = model.position
                    this.dataList = model.list
                }
            }
        }
    }

    companion object {
        private const val PASS_ANALYSIS_DATA = "PASS_ANALYSIS_DATA"

        fun newInstance(analysisModel: AnalysisModel): Fragment {
            val args = Bundle().apply {
                putParcelable(PASS_ANALYSIS_DATA, analysisModel)
            }

            return AnalysisDataFragment().apply {
                arguments = args
            }
        }
    }
}