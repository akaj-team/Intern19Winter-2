package asiantech.internship.summer.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CheckView(context: Context, attributeSet : AttributeSet) : View(context, attributeSet) {
    private lateinit var paint: Paint
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPaint()
        //4 cạnh
        canvas?.drawLine(10f, 10f, width.toFloat(), 10f, paint)
        canvas?.drawLine(10f, 10f, 10f, width.toFloat() + (width.toFloat() / 8), paint)
        canvas?.drawLine(10f, width.toFloat() + (width.toFloat() / 8), width.toFloat(), width.toFloat() + (width.toFloat() / 8), paint)
        canvas?.drawLine(width.toFloat() - 3, 10f, width.toFloat() - 3, width.toFloat() + (width.toFloat() / 8), paint)
        //9 đường ngang
        for (i in 1..8) {
            canvas?.drawLine(10f, width.toFloat() / 8 * i, width.toFloat(), width.toFloat() / 8 * i, paint)
        }
        //4 đường dọc trên
        for (i in 1..7) {
            canvas?.drawLine(width.toFloat() / 8 * i, 10f, width.toFloat() / 8 * i, (width.toFloat() / 8) * 4, paint)
        }
        //4 đường dọc dưới
        for (i in 1..7) {
            canvas?.drawLine(width.toFloat() / 8 * i, (width.toFloat() / 8) * 5, width.toFloat() / 8 * i, (width.toFloat() / 8) * 9, paint)
        }
        //2 đường chéo trên
        canvas?.drawLine(width.toFloat() / 8 * 3, 10f, width.toFloat() / 8 * 5, width.toFloat() / 8 * 2, paint)
        canvas?.drawLine(width.toFloat() / 8 * 5, 10f, width.toFloat() / 8 * 3, width.toFloat() / 8 * 2, paint)
        //2 đường chéo dưới
        canvas?.drawLine(width.toFloat() / 8 * 3, width.toFloat() / 8 * 7, width.toFloat() / 8 * 5, width.toFloat() / 8 * 9, paint)
        canvas?.drawLine(width.toFloat() / 8 * 5, width.toFloat() / 8 * 7, width.toFloat() / 8 * 3, width.toFloat() / 8 * 9, paint)
        //góc
        fun fourCorners(x: Int, y: Int) {
            canvas?.drawLine(width.toFloat() / 8 * x - 10f, width.toFloat() / 8 * y - 10f, width.toFloat() / 8 * x - 10f, width.toFloat() / 8 * y - 40f, paint)
            canvas?.drawLine(width.toFloat() / 8 * x + 10f, width.toFloat() / 8 * y + 10f, width.toFloat() / 8 * x + 10f, width.toFloat() / 8 * y + 40f, paint)
            canvas?.drawLine(width.toFloat() / 8 * x - 10f, width.toFloat() / 8 * y + 10f, width.toFloat() / 8 * x - 10f, width.toFloat() / 8 * y + 40f, paint)
            canvas?.drawLine(width.toFloat() / 8 * x + 10f, width.toFloat() / 8 * y - 10f, width.toFloat() / 8 * x + 10f, width.toFloat() / 8 * y - 40f, paint)
            canvas?.drawLine(width.toFloat() / 8 * x - 10f, width.toFloat() / 8 * y - 10f, width.toFloat() / 8 * x - 40f, width.toFloat() / 8 * y - 10f, paint)
            canvas?.drawLine(width.toFloat() / 8 * x + 10f, width.toFloat() / 8 * y + 10f, width.toFloat() / 8 * x + 40f, width.toFloat() / 8 * y + 10f, paint)
            canvas?.drawLine(width.toFloat() / 8 * x - 10f, width.toFloat() / 8 * y + 10f, width.toFloat() / 8 * x - 40f, width.toFloat() / 8 * y + 10f, paint)
            canvas?.drawLine(width.toFloat() / 8 * x + 10f, width.toFloat() / 8 * y - 10f, width.toFloat() / 8 * x + 40f, width.toFloat() / 8 * y - 10f, paint)
        }

        fun twoCornersLeft(y: Int) {
            canvas?.drawLine(10 + 10f, width.toFloat() / 8 * y + 10f, 10 + 10f, width.toFloat() / 8 * y + 40f, paint)
            canvas?.drawLine(10 + 10f, width.toFloat() / 8 * y - 10f, 10 + 10f, width.toFloat() / 8 * y - 40f, paint)
            canvas?.drawLine(10 + 10f, width.toFloat() / 8 * y + 10f, 10 + 40f, width.toFloat() / 8 * y + 10f, paint)
            canvas?.drawLine(10 + 10f, width.toFloat() / 8 * y - 10f, 10 + 40f, width.toFloat() / 8 * y - 10f, paint)
        }

        fun twoCornersRight(y: Int) {
            canvas?.drawLine(width.toFloat() - 10f, width.toFloat() / 8 * y - 10f, width.toFloat() - 10f, width.toFloat() / 8 * y - 40f, paint)
            canvas?.drawLine(width.toFloat() - 10f, width.toFloat() / 8 * y + 10f, width.toFloat() - 10f, width.toFloat() / 8 * y + 40f, paint)
            canvas?.drawLine(width.toFloat() - 10f, width.toFloat() / 8 * y - 10f, width.toFloat() - 40f, width.toFloat() / 8 * y - 10f, paint)
            canvas?.drawLine(width.toFloat() - 10f, width.toFloat() / 8 * y + 10f, width.toFloat() - 40f, width.toFloat() / 8 * y + 10f, paint)

        }
        twoCornersLeft(3)
        twoCornersLeft(6)
        twoCornersRight(6)
        twoCornersRight(3)
        fourCorners(1, 2)
        fourCorners(7, 2)
        fourCorners(2, 3)
        fourCorners(4, 3)
        fourCorners(6, 3)
        fourCorners(6, 6)
        fourCorners(4, 6)
        fourCorners(2, 6)
        fourCorners(1, 7)
        fourCorners(7, 7)
    }

    private fun initPaint() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setColor(Color.BLACK)
        paint.strokeWidth = 5f
    }
}
