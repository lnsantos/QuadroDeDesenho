package com.lnsantos.features.drawer

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class PaintingDrawView
    constructor(context: Context) : View(context, null){

    private val LOG_TAG = this::class.java.simpleName

    private val settingCalculateInset = 40

    private var painting = Paint()
    private var pathDraw = Path()
    private var brushColor = 0
    private var paintingBackgroundColor = 0

    private var extraCanvas: Canvas? = null
    private var extraBitmap: Bitmap? = null
    private var frameEdge: Rect? = null

    private var lastX = 0F
    private var lastY = 0F

    init {
        onInitPaintingDraw()
    }

    override fun onSizeChanged(width: Int, height: Int, oldwWidth: Int, oldheight: Int) {
        super.onSizeChanged(width, height, oldwWidth, oldheight)

        PaintingDrawUtils.createBitmap(width, height)?.run {
            extraBitmap = this
            extraCanvas = Canvas(this)
            addLog("on size changed with sucess")
        }

        addLog("seted color background")
        extraCanvas?.drawColor(paintingBackgroundColor)

        frameEdge = Rect(settingCalculateInset,
                         settingCalculateInset,
                        width - settingCalculateInset,
                        height - settingCalculateInset)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            onDrawFrame(it)
            onDrawPainting(it)
        }
    }

    private fun onDrawFrame(canvas: Canvas) {
        frameEdge?.let { frame ->
            addLog("on draw frame")
            canvas.drawRect(frame, painting)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val axisX = event.x
        val axisY = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> onTouchStart(axisX, axisY)
            MotionEvent.ACTION_MOVE -> onTouchMove(axisX, axisY)
            MotionEvent.ACTION_UP -> onTouchUp()
        }

        return super.onTouchEvent(event)
    }

    private fun onDrawPainting(canvas: Canvas) {
        val bitmapWidthAndHeight = extraBitmap!!
        canvas.drawBitmap(bitmapWidthAndHeight, 0f,0f, null)

        addLog("on Draw Painting")
    }

    private fun onInitPaintingDraw() {
        paintingBackgroundColor = PaintingDrawUtils.getDefaultBackgroundColor(resources)
        brushColor = PaintingDrawUtils.getDefaultBrushColor(resources)
        setupPainting()
    }

    private fun setupPainting() {
        painting.apply {
            color = brushColor
            isAntiAlias = true
            isDither = true
            style = PaintingDrawUtils.styleStroke
            strokeJoin = PaintingDrawUtils.styleStrokeRound
            strokeCap = PaintingDrawUtils.styleStrokeCap
            strokeWidth = PaintingDrawUtils.defaultStrokeWidth
        }
    }

    private fun onTouchStart(positionX: Float, positionY: Float) {
        pathDraw.moveTo(positionX, positionY)
        saveLastPositionMovement(positionX, positionY)
    }

    private fun onTouchMove(positionX: Float, positionY: Float) {
        val distanceX = abs(positionX - lastX)
        val distanceY = abs(positionY - lastY)

        if (distanceX >= PaintingDrawUtils.TOUCH_TOLERANCE ||
            distanceY >= PaintingDrawUtils.TOUCH_TOLERANCE) {

            val limitDistanceDrawPositionX = ( positionX + lastX ) / 2
            val limitDistanceDrawPositionY = ( positionY + lastY ) / 2

            pathDraw.quadTo(lastX, lastY, limitDistanceDrawPositionX, limitDistanceDrawPositionY)
            saveLastPositionMovement(positionX, positionY)
            extraCanvas?.drawPath(pathDraw, painting)
        }

        invalidate()
    }

    private fun onTouchUp(){
        pathDraw.reset()
    }

    private fun saveLastPositionMovement(positionX: Float, positionY: Float) {
        this.lastX = positionX
        this.lastY = positionY
        addLog("Position X $positionX / Position Y $positionY")
    }

    private fun addLog(message: String){
        Log.i(LOG_TAG, "Warning : $message")
    }
}