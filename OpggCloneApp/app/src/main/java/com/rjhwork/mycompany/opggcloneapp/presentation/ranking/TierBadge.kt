package com.rjhwork.mycompany.opggcloneapp.presentation.ranking

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.TextViewCompat
import com.rjhwork.mycompany.opggcloneapp.extension.dip

class TierBadge(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {

    private val ct = context
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val verticalPadding = dip(2f)
        val horizontalPadding = dip(2f)
        setPadding(
            horizontalPadding,
            verticalPadding,
            horizontalPadding,
            verticalPadding
        )
        setTextColor(Color.WHITE)
        textAlignment = TEXT_ALIGNMENT_CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        canvas.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            paint
        )
        super.onDraw(canvas)
    }

    fun setBadgeTextColor(text: CharSequence, @ColorRes color:Int) {
        this.text = text
        paint.color = getColor(ct, color)
        invalidate()
    }
}