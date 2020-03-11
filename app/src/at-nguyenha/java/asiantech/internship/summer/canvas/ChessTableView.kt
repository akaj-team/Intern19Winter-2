package asiantech.internship.summer.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessTableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val lineColor = Color.BLACK
    private var borderWidthIn = 4.0f
    private var borderWidthOut = 10.0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

//    private fun init(@Nullable set: AttributeSet) {
//    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBorderOut(canvas)
        drawBorderIn(canvas)

        //draw horizontal line
        canvas.drawLine(90f, 202.5f, 990f, 202.5f, paint)
        canvas.drawLine(90f, 315f, 990f, 315f, paint)
        canvas.drawLine(90f, 427.5f, 990f, 427.5f, paint)
        canvas.drawLine(90f, 540f, 990f, 540f, paint)
        canvas.drawLine(90f, 652.5f, 990f, 652.5f, paint)
        canvas.drawLine(90f, 765f, 990f, 765f, paint)
        canvas.drawLine(90f, 877.5f, 990f, 877.5f, paint)
        canvas.drawLine(90f, 990f, 990f, 990f, paint)

        //draw vertical line
        canvas.drawLine(202.5f, 90f, 202.5f, 540f, paint)
        canvas.drawLine(202.5f, 652.5f, 202.5f, 1102.5f, paint)
        canvas.drawLine(315f, 90f, 315f, 540f, paint)
        canvas.drawLine(315f, 652.5f, 315f, 1102.5f, paint)
        canvas.drawLine(427.5f, 90f, 427.5f, 540f, paint)
        canvas.drawLine(427.5f, 652.5f, 427.5f, 1102.5f, paint)
        canvas.drawLine(540f, 90f, 540f, 540f, paint)
        canvas.drawLine(540f, 652.5f, 540f, 1102.5f, paint)
        canvas.drawLine(652.5f, 90f, 652.5f, 540f, paint)
        canvas.drawLine(652.5f, 652.5f, 652.5f, 1102.5f, paint)
        canvas.drawLine(765f, 90f, 765f, 540f, paint)
        canvas.drawLine(765f, 652.5f, 765f, 1102.5f, paint)
        canvas.drawLine(877.5f, 90f, 877.5f, 540f, paint)
        canvas.drawLine(877.5f, 652.5f, 877.5f, 1102.5f, paint)

        //draw diagonal line
        canvas.drawLine(427.5f, 90f, 652.5f, 315f, paint)
        canvas.drawLine(427.5f, 315f, 652.5f, 90f, paint)
        canvas.drawLine(427.5f, 1102.5f, 652.5f, 877.5f, paint)
        canvas.drawLine(427.5f, 877.5f, 652.5f, 1102.5f, paint)
    }

    private fun drawBorderOut(canvas: Canvas) {
        paint.color = lineColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidthOut
        canvas.drawRect(70F, 70F, 1010F, 1122.5F, paint)
    }

    private fun drawBorderIn(canvas: Canvas) {
        paint.strokeWidth = borderWidthIn
        canvas.drawRect(90F, 90F, 990F, 1102.5F, paint)
    }
}
