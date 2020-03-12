package asiantech.internship.summer.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessTableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val lineColor = Color.BLACK
    private val borderWidthIn = 4.0f
    private val borderWidthOut = 10.0f
    private val mPaintBorder = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var size: Float = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        size = (width / 10).toFloat()
        initPaint()
        canvas.drawRect(size - 20f, size - 20f, size * 9 + 20f, size * 10 + 20f, mPaintBorder)
        canvas.drawRect(size, size, size * 9, size * 10, mPaint)
        drawVerticalLine(canvas)
        drawHorizontalLine(canvas)
        drawDiagonalLine(size * 5, size * 2, canvas)
        drawDiagonalLine(size * 5, size * 9, canvas)
        drawSmallLine(size * 2, size * 3, canvas)
        drawSmallLine(size * 8, size * 3, canvas)
        drawSmallLine(size * 2, size * 8, canvas)
        drawSmallLine(size * 8, size * 8, canvas)
        drawSmallLine(size * 3, size * 4, canvas)
        drawSmallLine(size * 5, size * 4, canvas)
        drawSmallLine(size * 7, size * 4, canvas)
        drawSmallLine(size * 3, size * 7, canvas)
        drawSmallLine(size * 5, size * 7, canvas)
        drawSmallLine(size * 7, size * 7, canvas)
        drawLeftPawn(size, size * 4, canvas)
        drawLeftPawn(size, size * 7, canvas)
        drawRightPawn(size * 9, size * 4, canvas)
        drawRightPawn(size * 9, size * 7, canvas)
    }

    private fun initPaint() {
        mPaintBorder.color = lineColor
        mPaintBorder.style = Paint.Style.STROKE
        mPaintBorder.strokeWidth = borderWidthOut
        mPaint.color = lineColor
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = borderWidthIn
    }

    private fun drawSmallLine(dX: Float, dY: Float, canvas: Canvas) {
        canvas.drawLine(dX - 10, dY - 10, dX - 30, dY - 10, mPaint)
        canvas.drawLine(dX - 10, dY + 10, dX - 30, dY + 10, mPaint)
        canvas.drawLine(dX + 10, dY - 10, dX + 30, dY - 10, mPaint)
        canvas.drawLine(dX + 10, dY + 10, dX + 30, dY + 10, mPaint)
        canvas.drawLine(dX - 10, dY - 10, dX - 10, dY - 30, mPaint)
        canvas.drawLine(dX - 10, dY + 10, dX - 10, dY + 30, mPaint)
        canvas.drawLine(dX + 10, dY - 10, dX + 10, dY - 30, mPaint)
        canvas.drawLine(dX + 10, dY + 10, dX + 10, dY + 30, mPaint)
    }

    private fun drawLeftPawn(dX: Float, dY: Float, canvas: Canvas) {
        canvas.drawLine(dX + 10, dY - 10, dX + 30, dY - 10, mPaint)
        canvas.drawLine(dX + 10, dY + 10, dX + 30, dY + 10, mPaint)
        canvas.drawLine(dX + 10, dY - 30, dX + 10, dY - 10, mPaint)
        canvas.drawLine(dX + 10, dY + 30, dX + 10, dY + 10, mPaint)
    }

    private fun drawRightPawn(dX: Float, dY: Float, canvas: Canvas) {
        canvas.drawLine(dX - 10, dY - 10, dX - 30, dY - 10, mPaint)
        canvas.drawLine(dX - 10, dY + 10, dX - 30, dY + 10, mPaint)
        canvas.drawLine(dX - 10, dY - 30, dX - 10, dY - 10, mPaint)
        canvas.drawLine(dX - 10, dY + 30, dX - 10, dY + 10, mPaint)
    }

    private fun drawHorizontalLine(canvas: Canvas) {
        for (i in 2..9) {
            canvas.drawLine(size, size * i, size * 9, size * i, mPaint)
        }
    }

    private fun drawVerticalLine(canvas: Canvas) {
        for (i in 2..8) {
            canvas.drawLine(size * i, size * 1, size * i, size * 5, mPaint)
            canvas.drawLine(size * i, size * 6, size * i, size * 10, mPaint)
        }
    }

    private fun drawDiagonalLine(dX: Float, dY: Float, canvas: Canvas) {
        canvas.drawLine(dX - size, dY - size, dX + size, dY + size, mPaint)
        canvas.drawLine(dX - size, dY + size, dX + size, dY - size, mPaint)
    }
}
