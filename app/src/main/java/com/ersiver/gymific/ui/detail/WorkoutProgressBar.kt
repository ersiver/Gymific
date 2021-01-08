package com.ersiver.gymific.ui.detail

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import com.ersiver.gymific.R
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Custom ProgressBar that displays workout
 * time in the center of a circular progress bar.
 */

private const val GESTURE_THRESHOLD_DP = 76F

class WorkoutProgressBar(context: Context, attrs: AttributeSet) :
    ProgressBar(context, attrs, R.attr.progressBarStyle, R.style.Gymific_ProgressBar) {
    private val textPaint: TextPaint
    private var timeText = "-:--"
    private var duration = 0L
    private val maxProgress = 100
    private val fontHeight: Int
    private var posX = 0
    private var posY = 0

    init {
        val res = resources
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.density = res.displayMetrics.density
        textPaint.textSize = GESTURE_THRESHOLD_DP.toInt() * res.displayMetrics.scaledDensity
        textPaint.color = ResourcesCompat.getColor(res, R.color.color_secondary, null)
        textPaint.isAntiAlias = true
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        val fontMetrics = textPaint.fontMetrics
        fontHeight = (fontMetrics.descent + fontMetrics.ascent).toInt()
    }

    fun setDuration(time: Long) {
        duration = TimeUnit.MILLISECONDS.toSeconds(time)
    }

    /**
     * Calculate remaining workout time into min:sec format,
     * and draw it in the center of the circular progress bar.
     *
     * Calculate progress and update the 'moving' ring.
     */
    @Synchronized
    fun updateProgressBar(timeRemainingMilis: Long) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemainingMilis) % 60
        val totalSeconds = TimeUnit.MILLISECONDS.toSeconds(timeRemainingMilis)
        val seconds = totalSeconds % 60
        timeText = String.format(Locale.ROOT, "%1d:%02d", minutes, seconds)
        val progress = maxProgress - totalSeconds * maxProgress / duration
        setProgress(progress.toInt())
        invalidate()
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(timeText, posX.toFloat(), posY.toFloat(), textPaint)
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        posX = measuredWidth / 2
        posY = measuredHeight / 2 - fontHeight / 2
    }
}