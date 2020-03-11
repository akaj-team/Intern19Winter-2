package asiantech.internship.summer.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChineseChess(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var colorLine = Color.BLACK
    private var borderWidthOut = 10.0f
    private var borderWidthInt = 4.0f
    private var start = 70F
    private var startBorder = 50F
    private var size = 0F
    private val marginLeft = 10F
    private val marginRight = 50F


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        size = ((width.toFloat() - 140) / 8)
        drawBackground(canvas)
        canvas?.let { drawBorderOut(it) }
        canvas?.let { drawBorderIn(it) }
        canvas?.let { drawMove(it) }
    }

    private fun drawBackground(canvas: Canvas?) {
        paint.color = colorLine
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidthInt
        canvas?.apply {
            for (i in 1..8) {
                drawLine(start, start + size * i, width - start, start + size * i, paint)
            }
        }
        canvas?.apply {
            for (i in 1..7) {
                drawLine(start + size * i, start, start + size * i, start + size * 4, paint)
                drawLine(start + size * i, start + size * 5, start + size * i, start + size * 9, paint)
            }
        }
        canvas?.apply {
            drawLine(start + size * 3, start, start + size * 5, start + size * 2, paint)
            drawLine(start + size * 3, start + size * 2, start + size * 5, start, paint)
            drawLine(start + size * 3, start + size * 7, start + size * 5, start + size * 9, paint)
            drawLine(start + size * 3, start + size * 9, start + size * 5, start + size * 7, paint)
        }
    }

    private fun drawMove(canvas: Canvas) {
        paint.color = colorLine
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidthInt
        for (i in 0..8) {
            when (i) {
                0 -> {
                    drawRight(canvas, start + size * i, start + size * 3, start + size * i, start + size * 3)
                    drawRight(canvas, start + size * i, start + size * 6, start + size * i, start + size * 6)
                }
                2, 4, 6 -> {
                    drawCorner(canvas, start + size * i, start + size * 3, start + size * i, start + size * 3)
                    drawCorner(canvas, start + size * i, start + size * 6, start + size * i, start + size * 6)
                }
                1, 7 -> {
                    drawCorner(canvas, start + size * i, start + size * 2, start + size * i, start + size * 2)
                    drawCorner(canvas, start + size * i, start + size * 7, start + size * i, start + size * 7)
                }
                8 -> {
                    drawLeft(canvas, start + size * i, start + size * 3, start + size * i, start + size * 3)
                    drawLeft(canvas, start + size * i, start + size * 6, start + size * i, start + size * 6)
                }
            }
        }

    }

    private fun drawLeft(canvas: Canvas, startX: Float, startY: Float, endX: Float, endY: Float) {
        canvas.apply {
            drawLine(startX - marginLeft, startY - marginLeft, endX - marginLeft, endY - marginRight, paint)
            drawLine(startX - marginLeft, startY - marginLeft, endX - marginRight, endY - marginLeft, paint)
            drawLine(startX - marginLeft, startY + marginLeft, endX - marginLeft, endY + marginRight, paint)
            drawLine(startX - marginLeft, startY + marginLeft, endX - marginRight, endY + marginLeft, paint)
        }
    }

    private fun drawRight(canvas: Canvas, startX: Float, startY: Float, endX: Float, endY: Float) {
        canvas.apply {
            drawLine(startX + marginLeft, startY + marginLeft, endX + marginRight, endY + marginLeft, paint)
            drawLine(startX + marginLeft, startY + marginLeft, endX + marginLeft, endY + marginRight, paint)
            drawLine(startX + marginLeft, startY - marginLeft, endX + marginRight, endY - marginLeft, paint)
            drawLine(startX + marginLeft, startY - marginLeft, endX + marginLeft, endY - marginRight, paint)
        }
    }

    private fun drawCorner(canvas: Canvas, startX: Float, startY: Float, endX: Float, endY: Float) {
        canvas.apply {
            drawLine(startX - marginLeft, startY - marginLeft, endX - marginLeft, endY - marginRight, paint)
            drawLine(startX - marginLeft, startY - marginLeft, endX - marginRight, endY - marginLeft, paint)
            drawLine(startX - marginLeft, startY + marginLeft, endX - marginLeft, endY + marginRight, paint)
            drawLine(startX - marginLeft, startY + marginLeft, endX - marginRight, endY + marginLeft, paint)
            drawLine(startX + marginLeft, startY + marginLeft, endX + marginRight, endY + marginLeft, paint)
            drawLine(startX + marginLeft, startY + marginLeft, endX + marginLeft, endY + marginRight, paint)
            drawLine(startX + marginLeft, startY - marginLeft, endX + marginRight, endY - marginLeft, paint)
            drawLine(startX + marginLeft, startY - marginLeft, endX + marginLeft, endY - marginRight, paint)
        }
    }

    private fun drawBorderOut(canvas: Canvas) {
        paint.color = colorLine
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidthOut
        canvas.drawRect(startBorder, startBorder, (width - startBorder), start + size * 9 + 20, paint)
    }

    private fun drawBorderIn(canvas: Canvas) {
        paint.strokeWidth = borderWidthInt
        canvas.drawRect(start, start, (width - start), start + size * 9, paint)
    }

}
