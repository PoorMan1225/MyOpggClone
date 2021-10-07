package com.rjhwork.mycompany.opggcloneapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalysisModel(
    val list:List<BindAnalysisData>?,
    val myPuuid:String?,
    val position:Int,
    val sumWin:Int,
    val sumLose:Int,
): Parcelable