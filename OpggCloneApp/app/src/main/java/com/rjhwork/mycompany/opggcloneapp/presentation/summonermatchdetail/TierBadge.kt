package com.rjhwork.mycompany.opggcloneapp.presentation.summonermatchdetail

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.rjhwork.mycompany.opggcloneapp.R
import com.rjhwork.mycompany.opggcloneapp.extension.dip

class TierBadge(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.green)
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
            height / 5f,
            height / 5f,
            paint
        )

        super.onDraw(canvas)
    }

    fun setBadgeText(text: CharSequence) {
        val pair = getBadgeTextAndColor(text)
        paint.color = ContextCompat.getColor(context, pair.second)
        this.text = pair.first
        if(pair.first == "-")
            width = dip(15f)
        invalidate()
    }

    private fun getBadgeTextAndColor(text: CharSequence): Pair<String, Int> {
        return when(text) {
            "IRON IV" -> Pair("I4", R.color.iron_color)
            "IRON III" -> Pair("I3", R.color.iron_color)
            "IRON II" -> Pair("I2", R.color.iron_color)
            "IRON I" -> Pair("I1", R.color.iron_color)
            "BRONZE IV" -> Pair("B4", R.color.bronze_color)
            "BRONZE III" -> Pair("B3", R.color.bronze_color)
            "BRONZE II" -> Pair("B2", R.color.bronze_color)
            "BRONZE I" -> Pair("B1", R.color.bronze_color)
            "SILVER IV" -> Pair("S4", R.color.silver_color)
            "SILVER III" -> Pair("S3", R.color.silver_color)
            "SILVER II" -> Pair("S2", R.color.silver_color)
            "SILVER I" -> Pair("S1", R.color.silver_color)
            "GOLD IV" -> Pair("G4", R.color.gold_color)
            "GOLD III" -> Pair("G3", R.color.gold_color)
            "GOLD II" -> Pair("G2", R.color.gold_color)
            "GOLD I" -> Pair("G1", R.color.gold_color)
            "PLATINUM IV" -> Pair("P4", R.color.platinum_color)
            "PLATINUM III" -> Pair("P3", R.color.platinum_color)
            "PLATINUM II" -> Pair("P2", R.color.platinum_color)
            "PLATINUM I" -> Pair("P1", R.color.platinum_color)
            "DIAMOND IV" -> Pair("D4", R.color.diamond_color)
            "DIAMOND III" -> Pair("D3", R.color.diamond_color)
            "DIAMOND II" -> Pair("D2", R.color.diamond_color)
            "DIAMOND I" -> Pair("D1", R.color.diamond_color)
            "MASTER I" -> Pair("M1", R.color.master_color)
            "GRANDMASTER I" -> Pair("GM1", R.color.master_color)
            "CHALLENGER I" -> Pair("C1", R.color.master_color)
            else -> Pair("-", R.color.unranked_color)
        }
    }
}