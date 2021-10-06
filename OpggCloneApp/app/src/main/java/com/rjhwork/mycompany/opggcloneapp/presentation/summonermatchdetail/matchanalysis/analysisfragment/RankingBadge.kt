package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail.matchanalysis.analysisfragment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.getColor
import com.rjhwork.mycompany.opggcloneapp.extension.dip

class RankingBadge(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
    }

    init {
        val verticalPadding = dip(2f)
        val horizontalPadding = dip(4f)
        setPadding(
            horizontalPadding,
            verticalPadding,
            horizontalPadding,
            verticalPadding
        )
        setTextColor(Color.WHITE)
        typeface = Typeface.DEFAULT_BOLD
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            height / 2f,
            height / 2f,
            paint
        )
        super.onDraw(canvas)
    }

    fun setBadgeTextColor(text: CharSequence, @ColorRes color:Int) {
        setBackgroundColor(getColor(context, color))
        this.text = text
        invalidate()
    }
}