package asiantech.internship.summer.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class WeightCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var widths = 0f
    private var heights = 0f
    private var weightList = List(13) { Random.nextInt(50, 120) }
    private var scaleAxisY = 0f
    private var path = Path()
    private var moveX = 0f
    private var startMove = 0f
    private var stopMove = 0f
    private var testMove = 0f
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        widths = width.toFloat()
        heights = height.toFloat()
        scaleAxisY = heights / 150
        drawNumberWeight(canvas)
        drawAxisLine(canvas)
        drawText(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startMove = x
//                Log.i("XXX", "Down")
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = x
                stopMove = x
                testMove = startMove - stopMove
//                Log.i("XXX", "X$x")
//                Log.i("XXX", "Y$y")
                path.reset()
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

    private fun drawNumberWeight(canvas: Canvas?) {

        val rangeWidth = widths / 10 - 15f
        var start = 0f - testMove

        weightList.forEachIndexed { index, it ->
            if (index < weightList.size - 1) {
                val startX = start
                val startY = heights - it * scaleAxisY
                val endX = start + rangeWidth
                val endY = heights - (weightList[index + 1]) * scaleAxisY
                canvas?.drawText("$it", start, startY - 30f, textPaint) //number
                canvas?.drawLine(startX, startY, endX, endY, linePaint)
                canvas?.drawCircle(startX, startY, 12f, pointPaint)
                canvas?.drawText("${index + 1}", start + 20f, heights - 20f, textPaint) // day time
                path.moveTo(startX, startY)
                path.lineTo(start, heights)
                canvas?.drawPath(path, dashedLinePaint)
                start += rangeWidth
            }
        }
    }

    private fun drawAxisLine(canvas: Canvas) {
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

    private fun drawText(canvas: Canvas) {
        canvas.drawText("Kg", 20f, 30f, textPaint)
    }

    private val dashedLinePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f
        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)

    }
    private val linePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLACK
        strokeWidth = 7f
        style = Paint.Style.FILL
    }

    private val pointPaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.RED
    }

    private val textPaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.BLUE
        textSize = 30f
    }

    private val axisLinePaint = Paint().apply {
        Paint.ANTI_ALIAS_FLAG
        color = Color.RED
        strokeWidth = 10f
    }


}
