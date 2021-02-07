package com.lnsantos.features.drawer

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat
import com.lnsantos.features.R

object PaintingDrawUtils {

    const val TOUCH_TOLERANCE = 4f

    val styleStroke = Paint.Style.STROKE
    val styleStrokeRound = Paint.Join.ROUND
    val styleStrokeCap = Paint.Cap.ROUND
    val defaultStrokeWidth = 12F

    fun getDefaultBackgroundColor(resources: Resources)
            = ResourcesCompat.getColor(resources, R.color.painting_draw_default_background, null)

    fun getDefaultBrushColor(resources: Resources)
            = ResourcesCompat.getColor(resources, R.color.painting_draw_default_brush, null)

    fun createBitmap(width: Int, height: Int)
           = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

}