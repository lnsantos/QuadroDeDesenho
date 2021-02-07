package com.lnsantos.features.drawer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class PaintingDrawView@JvmOverloads constructor(
    context: Context?,
    attributeSet: AttributeSet? = null
) :
    View(context){

    private val LOG_TAG = this::class.java.simpleName

    private val settingCalculateInset = 40

    private var painting : Paint = Paint()
    private var pathDraw : Path = Path()

    private var brushColor = 0
    private var paintingBackgroundColor = 0

    private var extraCanvas: Canvas? = null
    private var extraBitmap: Bitmap? = null
    private var frameEdge: Rect? = null

    private var lastX = 0F
    private var lastY = 0F

    init {
        onInitPaintingDraw()
        setupPainting()
    }

    override fun onSizeChanged(width: Int, height: Int, oldwWidth: Int, oldheight: Int) {
        super.onSizeChanged(width, height, oldwWidth, oldheight)

        extraBitmap = PaintingDrawUtils.createBitmap(width, height)
        extraCanvas = Canvas(extraBitmap!!)
        extraCanvas!!.drawColor(paintingBackgroundColor)

        frameEdge = Rect(settingCalculateInset,
                         settingCalculateInset,
                        width - settingCalculateInset,
                        height - settingCalculateInset)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap!!, 0f,0f, null)
        canvas.drawRect(frameEdge!!, painting)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val axisX = event.x
        val axisY = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> onTouchStart(axisX, axisY)
            MotionEvent.ACTION_MOVE -> {
                onTouchMove(axisX, axisY)
                invalidate()
            }
            MotionEvent.ACTION_UP -> onTouchUp()
            else -> {
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    private fun onInitPaintingDraw() {
        paintingBackgroundColor = PaintingDrawUtils.getDefaultBackgroundColor(resources)
        brushColor = PaintingDrawUtils.getDefaultBrushColor(resources)
    }

    private fun setupPainting() {
        painting.color = brushColor
        painting.isAntiAlias = true
        painting.isDither = true
        painting.style = Paint.Style.STROKE
        painting.strokeJoin = Paint.Join.ROUND
        painting.strokeCap = Paint.Cap.ROUND
        painting.strokeWidth = 20F
    }

    private fun onTouchStart(positionX: Float, positionY: Float) {
        pathDraw.moveTo(positionX, positionY)
        saveLastPositionMovement(positionX, positionY)
    }

    private fun onTouchMove(positionX: Float, positionY: Float) {
        val distanceX = abs(positionX - lastX)
        val distanceY = abs(positionY - lastY)

        if (distanceX >= 4 || distanceY >= 4) {

            val limitDistanceDrawPositionX = ( positionX + lastX ) / 2
            val limitDistanceDrawPositionY = ( positionY + lastY ) / 2

            pathDraw.quadTo(lastX, lastY, limitDistanceDrawPositionX, limitDistanceDrawPositionY)
            saveLastPositionMovement(positionX, positionY)
            extraCanvas!!.drawPath(pathDraw, Paint().apply {
                color = Color.RED
            })
        }

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