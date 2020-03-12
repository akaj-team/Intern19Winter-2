package asiantech.internship.summer.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class WeightChart(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText = Paint()
    private val paintPoint = Paint()
    private var weightList = List(13) { Random.nextInt(50, 120) }
    private var colorLine = Color.BLACK
    private val margin = 50F
    private var borderChart = 4.0f
    private var size = 0.0f
    private var widths = 0f
    private var heights = 0f
    private var start = 0f
    private var ranger = 0f
    private var path = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        widths = width.toFloat()
        heights = height.toFloat()
        size = (height.toFloat() / 140)
        ranger = (height.toFloat() / 14)
        weightChart(canvas)
        monthlyWeight(canvas)
    }

    private fun weightChart(canvas: Canvas?) {
        paint.color = colorLine
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderChart
        paintText.color = Color.RED
        paintText.textSize = 40f
        canvas?.apply {
            drawLine(margin, heights - margin, margin, margin, paint)

            for (i in 1..13) {
                drawLine(margin / 2, heights - size * 10 * i, margin, heights - size * 10 * i, paint)
                drawText("${i}0",0F,heights-30 - size * 10 * i,paintText)
            }
            for (i in 0..11) {
                drawLine(margin + size * 20 * i, heights - margin, margin + size * 20 * i, heights - margin / 2, paint)
                drawText("${i+1}",margin + size * 20 * i,heights,paintText)
            }
            drawLine(margin, heights - margin, widths - margin, heights - margin, paint)
        }
    }

    private fun monthlyWeight(canvas: Canvas?) {
        paintPoint.color = Color.RED
        paintText.color = Color.BLUE
        weightList.forEachIndexed { index, it ->
            if (index < weightList.size - 1) {
                val startX = margin + start
                val startY = heights - it * size
                val endX = margin + start + size * 20
                val endY = heights - (weightList[index + 1]) * size
                canvas?.apply {
                    if (index < 11) {
                        drawLine(startX, startY, endX, endY, paint)
                    }
                    drawCircle(startX, startY, 15f, paintPoint)
                    drawText("$it",startX-margin,startY-margin,paintText)
                }
                path.moveTo(startX, startY)
                path.lineTo(startX, heights-margin)
                canvas?.drawPath(path, dashedLinePaint)
                start += size * 20
            }
        }
    }

    private val dashedLinePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = borderChart
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }
}


