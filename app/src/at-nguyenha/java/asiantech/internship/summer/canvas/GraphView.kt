package asiantech.internship.summer.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class GraphView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaintLine = Paint()
    private val mPaintPivot = Paint()
    private val mPaintLineDash = Paint(Paint.DITHER_FLAG)
    private val mPaintText = Paint()
    private val mPaintDot = Paint()
    private var size = 0f
    private var sizeInt = 0
    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        size = (width / 11).toFloat()
        sizeInt = ( width /11)
        initPaint()
        drawGraph(canvas)
        drawWeight(canvas)
        drawMonth(canvas)
        drawDot(canvas)
    }

    private fun initPaint() {
        mPaintLine.style = Paint.Style.STROKE
        mPaintLine.strokeWidth = 4f
        mPaintLine.color = Color.BLACK
        mPaintPivot.style = Paint.Style.STROKE
        mPaintPivot.strokeWidth = 6f
        mPaintPivot.color = Color.BLACK
        mPaintText.textSize = 18f
        mPaintText.strokeWidth = 4f
        mPaintText.color = Color.BLACK
        mPaintDot.style = Paint.Style.FILL
    }

    private fun initLineDash(){
        mPaintLineDash.strokeWidth = 2f
        mPaintLineDash.style = Paint.Style.STROKE
        mPaintLineDash.setARGB(255, 0, 0, 0)
        mPaintLineDash.pathEffect = DashPathEffect(floatArrayOf(10f, 18f, 10f, 18f), 0f)
    }

    private fun drawGraph(canvas: Canvas) {
        canvas.drawText("0", size / 2, size * 14.5f, mPaintText)
        canvas.drawLine(size, size, size, size * 14, mPaintPivot)
        canvas.drawLine(size, size * 14, size * 14, size * 14, mPaintPivot)
        canvas.drawLine(size, size, size * 3 / 4, size * 5 / 4, mPaintPivot)
        canvas.drawLine(size, size, size * 5 / 4, size * 5 / 4, mPaintPivot)
    }

    private fun drawWeight(canvas: Canvas) {
        var countWeight = 13
        for (i in 10..120 step 10) {
            canvas.drawText("$i", size / 3, size * countWeight, mPaintText)
            canvas.drawText("Kg", size / 2, size, mPaintText)
            countWeight--
        }
    }

    private fun drawMonth(canvas: Canvas) {
        for (i in 1..12) {
            canvas.drawText("$i", size * (i + 1), size * 14.5f, mPaintText)
            canvas.drawText("Month", size * 14, size * 14.5f, mPaintText)
        }
    }

    private fun drawDot(canvas: Canvas) {
        initLineDash()
        var x1: Float
        var x2 = 0f
        var y1: Float
        var y2 = 0f
        for (i in 1..12) {
            x1 = x2
            y1 = y2
            x2 = size * (i + 1)
            y2 = size * (2..9).random()
            canvas.drawCircle(x2, y2, 10f, mPaintDot)
            path.moveTo(x2, y2)
            path.quadTo(x2, y2, size, y2)
            canvas.drawPath(path, mPaintLineDash)
            path.moveTo(x2,y2)
            path.quadTo(x2,y2,x2,size*14)
            canvas.drawPath(path, mPaintLineDash)

            if (x1 != 0f && y1 != 0f) {
                canvas.drawLine(x1, y1, x2, y2, mPaintLine)
            }
        }
    }
}