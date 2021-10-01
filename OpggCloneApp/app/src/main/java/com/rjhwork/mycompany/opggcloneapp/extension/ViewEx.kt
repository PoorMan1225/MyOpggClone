package com.rjhwork.mycompany.opggcloneapp.extension
import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat

@Px
fun View.dip(dipValue: Float) = context.dip(dipValue)