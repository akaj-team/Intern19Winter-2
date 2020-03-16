package asiantech.internship.summer.canvas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class GraphView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var getW = 0f
    private var getH = 0f
    private var dX = 0f
    private var dY = 0f
    private var sizeInt = 0
    private val path = Path()
    private var isAddData = false
    private var weightList: MutableList<Int> = mutableListOf()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isAddData) {
            initData()
            isAddData = true
        }
        getW = width.toFloat()
        getH = height.toFloat()
        dX = (width / 6).toFloat()
        dY = (height / 15).toFloat()
        sizeInt = ( width /11)
        drawWeight(canvas)
        drawGraph(canvas)
        drawData(canvas)
    }

    private var moveX = 0f
    private var startMove = 0f
    private var stopMove = 0f
    private var distanceMove = 0f
    private var oldDistance = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                addData()
                startMove = x
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = x
                stopMove = x
                distanceMove = startMove - stopMove
                path.reset()
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                oldDistance += distanceMove
            }
        }
        return true
    }

    private fun drawGraph(canvas: Canvas) {
        canvas.drawLine(0f, dY * 14, width.toFloat(), dY * 14, mPaintPivot)
        canvas.drawLine(0f, dY, 0f, dY * 14, mPaintPivot)
    }

    private fun drawData(canvas: Canvas) {
        var start = -distanceMove - oldDistance
        weightList.forEachIndexed { index, i ->
            if (index < weightList.size - 1) {
                val startX = start
                val startY = ((i - 140f) / (-10f)) * dY
                val endX = startX + dX
                val endY = ((weightList[index + 1] - 140f) / (-10f)) * dY
                canvas.drawText(i.toString(), startX, startY - 50, mPaintText)
                canvas.drawText((index + 1).toString(), startX, dY * 14.5f, mPaintText)
                canvas.drawLine(startX, startY, endX, endY, mPaintLine)
                canvas.drawCircle(startX, startY, 10f, mPaintDot)
                path.moveTo(startX, startY)
                path.lineTo(start, dY * 14)
                canvas.drawPath(path, mPaintLineDash)
                path.moveTo(startX, startY)
                path.lineTo(0f, startY)
                canvas.drawPath(path, mPaintLineDash)
                start += dX
            }
        }
    }

    private fun drawWeight(canvas: Canvas) {
        canvas.drawText("Weight(Kg)", 0f, dY, mPaintText)
    }

    private fun initData() {
        for (i in 1..12) {
            weightList.add((50..120).random())
        }
    }

    private fun addData() {
        for (i in 1..5) {
            weightList.add((50..120).random())
        }
    }

    private val mPaintLine = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = Color.BLACK
    }

    private val mPaintText = Paint().apply {
        textSize = 18f
        strokeWidth = 4f
        color = Color.BLACK
    }

    private val mPaintPivot = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f
        color = Color.BLACK
    }

    private val mPaintDot = Paint().apply {
        style = Paint.Style.FILL
    }

    private val mPaintLineDash = Paint().apply {
        Paint.DITHER_FLAG
        strokeWidth = 2f
        style = Paint.Style.STROKE
        setARGB(255, 0, 0, 0)
        pathEffect = DashPathEffect(floatArrayOf(10f, 18f, 10f, 18f), 0f)
    }
}