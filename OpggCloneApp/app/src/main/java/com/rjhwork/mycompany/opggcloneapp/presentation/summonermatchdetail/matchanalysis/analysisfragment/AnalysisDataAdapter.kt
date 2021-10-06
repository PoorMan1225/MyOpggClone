package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.analysisfragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.domain.model.AnalysisModel

class AnalysisDataAdapter(context: Context): BaseAdapter() {

    var data = emptyList<AnalysisModel>()
    private var layoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): AnalysisModel {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.analysis_data_item, null)
        return view
    }
}