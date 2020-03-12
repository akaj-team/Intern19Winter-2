package asiantech.internship.summer.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class WeightCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var widths = 0f
    private var heights = 0f
    private var weightList = List(13) { Random.nextInt(50, 120) }.sorted()
    private var scaleAxisY = 0f
    private var path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        widths = width.toFloat()
        heights = height.toFloat()
        scaleAxisY = heights / 150
        drawNumberWeight(canvas)
        drawAxisLine(canvas)
    }

    private fun drawNumberWeight(canvas: Canvas?) {
        val rangeWidth = widths / 10 - 35f
        var start = 0f
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

    private val dashedLinePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 5f

        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)

    }
    private val linePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 7f
        style = Paint.Style.FILL
    }

    private val pointPaint = Paint().apply {
        color = Color.RED
    }

    private val textPaint = Paint().apply {
        color = Color.BLUE
        textSize = 30f
    }

    private val axisLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
    }

    private fun drawAxisLine(canvas: Canvas) {
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

}
