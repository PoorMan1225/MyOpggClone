package com.rjhwork.mycompany.opggcloneapp.extension

import android.content.Context

fun Context.dip(dipValue: Float) = (dipValue * resources.displayMetrics.density).toInt()