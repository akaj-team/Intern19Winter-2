package asiantech.internship.summer.canvas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log.d
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class WeightChart(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintPoint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var weightList = mutableListOf<Weight>()
    private var colorLine = Color.BLACK
    private val margin = 100F
    private var borderChart = 4.0f
    private var size = 0.0f
    private var widths = 0f
    private var heights = 0f
    private var start = 0f
    private var path = Path()
    private var isData = false
    private var stopX = 0f
    private var moveX = 0f
    private var distance = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        widths = width.toFloat()
        heights = height.toFloat()
        size = heights / 140
        if (!isData) {
            addData()
            isData = true
        }
        monthlyWeight(canvas)
        weightChart(canvas)
        d("TAG11", weightList.size.toString())
    }

    private fun weightChart(canvas: Canvas?) {
        paint.color = colorLine
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderChart
        paintText.color = Color.RED
        paintText.textSize = 40f
        canvas?.apply {
            drawLine(0F, heights - margin, 0F, margin, paint)
            for (i in 1..13) {
                drawLine(0F, heights - size * 10 * i, 0F, heights - size * 10 * i, paint)
                drawText("${i}0", 0F, heights - size * 10 * i, paintText)
            }
            drawLine(0F, heights - margin, widths - margin, heights - margin, paint)
        }
    }

    private fun addData() {
        weightList.apply {
            for (i in 1..12) {
                weightList.add(Weight(i, Random.nextInt(50, 120)))
            }
        }
    }

    private fun monthlyWeight(canvas: Canvas?) {
        val ranger = widths / 14
        paintPoint.color = Color.RED
        paintText.color = Color.BLUE
        start = -distance
        weightList.forEachIndexed { index, it ->
            if (index < weightList.size - 1) {
                val startX = ranger + start
                val startY = heights - it.weight * size
                val endX =  ranger * 2 + start
                val endY = heights - (weightList[index + 1].weight) * size
                canvas?.apply {
                    drawLine(startX, startY, endX, endY, paint)
                    drawCircle(startX, startY, 15f, paintPoint)
                    drawText("${it.weight}", startX, startY - margin, paintText)
                    drawText("${it.month}", startX, heights-margin/2, paintText)
                }
                path.moveTo(startX, startY)
                path.lineTo(startX, heights - margin)
                canvas?.drawPath(path, dashedLinePaint)
                start += ranger
            }
        }
    }

    private val dashedLinePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = borderChart
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                moveX = x
                if(weightList.size < 30){
                    addData()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                stopX = x
                distance = moveX - stopX
                path.reset()
                invalidate()
            }
        }
        return true
    }

    inner class Weight(var month: Int, var weight: Int)

}
