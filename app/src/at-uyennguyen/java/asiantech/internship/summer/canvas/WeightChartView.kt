package asiantech.internship.summer.canvas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*


class WeightChartView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private lateinit var paint: Paint
    private lateinit var paintText: Paint
    private lateinit var dashLine: Paint
    private lateinit var path: Path

    @SuppressLint("DrawAllocation")
    override fun invalidate() {
        super.invalidate()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPaint()
        initPaintText()
        initDashLine()

        //vertical
        canvas?.drawLine(20f, height.toFloat() - 20, 20f, 20f, paint)
        //spike the vertical
        canvas?.drawLine(20f, 20f, 0f, 60f, paint)
        canvas?.drawLine(20f, 20f, 40f, 60f, paint)
        //dividing the vertical
        for (i in 1..9) {
            canvas?.drawLine(10f, (height.toFloat() - 20) / 10 * i, 30f, (height.toFloat() - 20) / 10 * i, paint)
        }
        canvas?.drawText("Weight", 50f, 40f, paintText)
        canvas?.drawText("Month", width.toFloat() - 100f, height.toFloat() - 50, paintText)
        //horizontal
        canvas?.drawLine(20f, height.toFloat() - 20, width.toFloat(), height.toFloat() - 20, paint)
        //O center
        canvas?.drawText("0", 0f, height.toFloat() - 30, paintText)
        //dividing the horizontal
        for (i in 1..13) {
            if (i == 13) {
                //spike of the horizontal
                canvas?.drawLine(width.toFloat(), height.toFloat() - 20, width.toFloat() - 30, height.toFloat() - 40, paint)
                canvas?.drawLine(width.toFloat(), height.toFloat() - 20, width.toFloat() - 50, height.toFloat() + 20, paint)
            } else {
                //mouth
                canvas?.drawLine(width.toFloat() / 13 * i, height.toFloat() - 30, width.toFloat() / 13 * i, height.toFloat() - 10, paint)
                canvas?.drawText(i.toString(), width.toFloat() / 13 * i + 10, (height.toFloat() - 30), paintText)
            }
        }
        var startX = 20f
        var startY = height.toFloat() - 20
        for (i in 1..12) {
            val random = Random()
            val number = random.nextInt(9 - 1 + 1) + 1
            //dashed of horizontal
            path.moveTo(10f, (height.toFloat() - 20) / 10 * number)
            path.quadTo(10f, (height.toFloat() - 20) / 10 * number, width.toFloat() / 13 * i, (height.toFloat() - 20) / 10 * number)
            canvas?.drawPath(path, dashLine)
            //dashed of vertical
            path.moveTo(width.toFloat() / 13 * i, height.toFloat() - 30)
            path.quadTo(width.toFloat() / 13 * i, height.toFloat() - 30, width.toFloat() / 13 * i, (height.toFloat() - 20) / 10 * number)
            canvas?.drawPath(path, dashLine)
            //point
            canvas?.drawCircle(width.toFloat() / 13 * i, (height.toFloat() - 20) / 10 * number, 10f, paint)
            //link
            canvas?.drawLine(startX, startY, width.toFloat() / 13 * i, (height.toFloat() - 20) / 10 * number, paint)
            startX = width.toFloat() / 13 * i
            startY = (height.toFloat() - 20) / 10 * number
            //number weight
            val weight = 120 - (number - 1) * 10 + 10
            canvas?.drawText(weight.toString(), width.toFloat() / 13 * i, (height.toFloat() - 20) / 10 * number - 20f, paintText)
        }
    }

    private fun initPaint() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint.strokeWidth = 5f
    }

    private fun initPaintText() {
        paintText = Paint(Paint.ANTI_ALIAS_FLAG)
        paintText.color = Color.BLACK
        paintText.textSize = 30f
    }

    private fun initDashLine() {
        dashLine = Paint(Paint.ANTI_ALIAS_FLAG)
        path = Path()
        dashLine.setARGB(255, 0, 0, 0)
        dashLine.style = Paint.Style.STROKE
        dashLine.strokeWidth = 3f
        dashLine.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }
}
