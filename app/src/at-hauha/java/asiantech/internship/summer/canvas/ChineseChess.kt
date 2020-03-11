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


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        size  = ((width.toFloat()-140)/8)
        drawBackground(canvas)
        canvas?.let { drawBorderOut(it) }
        canvas?.let { drawBorderIn(it) }
        canvas?.let { drawMove(it) }
    }

    private fun drawBackground(canvas: Canvas?){
        paint.color = colorLine
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidthInt
        canvas?.apply {
            for(i in 1..8){
                drawLine(start,start+size*i,width-start,start+size*i,paint)
            }
        }
        canvas?.apply {
            for(i in 1..7){
                drawLine(start+size*i,start,start+size*i,start+size*4,paint)
                drawLine(start+size*i,start+size*5,start+size*i,start+size*9,paint)
            }
        }
        canvas?.apply {
            drawLine(start+size*3,start,start+size*5,start+size*2,paint)
            drawLine(start+size*3,start+size*2,start+size*5,start,paint)
            drawLine(start+size*3,start+size*7,start+size*5,start+size*9,paint)
            drawLine(start+size*3,start+size*9,start+size*5,start+size*7,paint)
        }
    }

    private fun drawMove(canvas: Canvas){
        paint.color = colorLine
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidthInt
        canvas.apply {
            for(i in 0..8){
                when(i){
                    0,2,4,6,8 ->{
                        if(i != 0){
                            drawLine(start+size*i-10,start+size*3-50,start+size*i-10,start+size*3-10,paint)
                            drawLine(start+size*i-50,start+size*3-10,start+size*i-10,start+size*3-10,paint)
                            drawLine(start+size*i-10,start+size*6-50,start+size*i-10,start+size*6-10,paint)
                            drawLine(start+size*i-50,start+size*6-10,start+size*i-10,start+size*6-10,paint)
                            drawLine(start+size*i-10,start+size*3+10,start+size*i-50,start+size*3+10,paint)
                            drawLine(start+size*i-10,start+size*3+10,start+size*i-10,start+size*3+50,paint)
                            drawLine(start+size*i-10,start+size*6+10,start+size*i-50,start+size*6+10,paint)
                            drawLine(start+size*i-10,start+size*6+10,start+size*i-10,start+size*6+50,paint)
                        }
                        if(i != 8){
                            drawLine(start+size*i+10,start+size*3-50,start+size*i+10,start+size*3-10,paint)
                            drawLine(start+size*i+10,start+size*3-10,start+size*i+50,start+size*3-10,paint)
                            drawLine(start+size*i+10,start+size*6-50,start+size*i+10,start+size*6-10,paint)
                            drawLine(start+size*i+10,start+size*6-10,start+size*i+50,start+size*6-10,paint)
                            drawLine(start+size*i+10,start+size*3+10,start+size*i+10,start+size*3+50,paint)
                            drawLine(start+size*i+10,start+size*3+10,start+size*i+50,start+size*3+10,paint)
                            drawLine(start+size*i+10,start+size*6+10,start+size*i+50,start+size*6+10,paint)
                            drawLine(start+size*i+10,start+size*6+10,start+size*i+10,start+size*6+50,paint)
                        }
                        if(i == 6){
                            drawLine(start+10,start+size*i-50,start+10,start+size*i-10,paint)
                            drawLine(start+50,start+size*i-10,start+10,start+size*i-10,paint)
                        }
                    }
                    1,3,5,7 ->{
                        if(i == 1 || i ==7){
                            drawLine(start+size*i-10,start+size*2-50,start+size*i-10,start+size*2-10,paint)
                            drawLine(start+size*i-50,start+size*2-10,start+size*i-10,start+size*2-10,paint)
                            drawLine(start+size*i-10,start+size*7-50,start+size*i-10,start+size*7-10,paint)
                            drawLine(start+size*i-50,start+size*7-10,start+size*i-10,start+size*7-10,paint)
                            drawLine(start+size*i+10,start+size*2-50,start+size*i+10,start+size*2-10,paint)
                            drawLine(start+size*i+50,start+size*2-10,start+size*i+10,start+size*2-10,paint)
                            drawLine(start+size*i+10,start+size*7-50,start+size*i+10,start+size*7-10,paint)
                            drawLine(start+size*i+50,start+size*7-10,start+size*i+10,start+size*7-10,paint)
                            drawLine(start+size*i-10,start+size*2+10,start+size*i-50,start+size*2+10,paint)
                            drawLine(start+size*i-10,start+size*2+10,start+size*i-10,start+size*2+50,paint)
                            drawLine(start+size*i-10,start+size*7+10,start+size*i-50,start+size*7+10,paint)
                            drawLine(start+size*i-10,start+size*7+10,start+size*i-10,start+size*7+50,paint)
                            drawLine(start+size*i+10,start+size*2+10,start+size*i+10,start+size*2+50,paint)
                            drawLine(start+size*i+10,start+size*2+10,start+size*i+50,start+size*2+10,paint)
                            drawLine(start+size*i+10,start+size*7+10,start+size*i+10,start+size*7+50,paint)
                            drawLine(start+size*i+10,start+size*7+10,start+size*i+50,start+size*7+10,paint)
                        }
                        if(i==3){
                            drawLine(start+10,start+size*i-50,start+10,start+size*i-10,paint)
                            drawLine(start+50,start+size*i-10,start+10,start+size*i-10,paint)
                        }
                    }
                }
            }
        }
    }

    private fun drawBorderOut(canvas: Canvas) {
        paint.color = colorLine
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidthOut
        canvas.drawRect(startBorder,startBorder,(width-startBorder),start+size*9+20,paint)
    }

    private fun drawBorderIn(canvas: Canvas) {
        paint.strokeWidth = borderWidthInt
        canvas.drawRect(start,start,(width-start),start+size*9,paint)
    }

}