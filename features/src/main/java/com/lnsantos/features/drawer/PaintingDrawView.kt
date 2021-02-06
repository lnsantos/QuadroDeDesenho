package com.lnsantos.features.drawer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View

class PaintingDrawView
    constructor(context: Context) : View(context, null){

    private var mPaint = Paint()
    private var mPath = Path()
    private var mDrawColor = 0
    private var mBackgroundColor = 0
    private var mExtraCanvas: Canvas? = null
    private var mExtraBitmap: Bitmap? = null

    init {
        onInitPaintingDraw()
    }

    private fun onInitPaintingDraw() {
        mBackgroundColor = PaintingDrawUtils.getDefaultBackgroundColor(resources)
        mDrawColor = PaintingDrawUtils.getDefaultBrushColor(resources)
        setupPainting()
    }

    private fun setupPainting() {
        mPaint.apply {
            color = mDrawColor
            isAntiAlias = true
            isDither = true
            style = PaintingDrawUtils.styleStroke
            strokeJoin = PaintingDrawUtils.styleStrokeRound
            strokeCap = PaintingDrawUtils.styleStrokeCap
            strokeWidth = PaintingDrawUtils.defaultStrokeWidth
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }

}