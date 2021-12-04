package com.ssafy.ssaign.src.main.sign

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ssafy.ssaign.src.main.sign.model.Point

class DrawSign : View {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { }

    var list = arrayListOf<Point>()

    var paint: Paint
    init {
        paint = Paint()
        paint.strokeWidth = 10F
        paint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        list.forEachIndexed { index, point ->
            if(index > 1 && point.isContinue) {
                canvas.drawLine(list[index-1].x, list[index-1].y, point.x, point.y, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                list.add(Point(event?.x, event?.y, false))
            }
            MotionEvent.ACTION_MOVE -> {
                list.add(Point(event?.x, event?.y, true))
            }
            MotionEvent.ACTION_UP -> {
                list.add(Point(event?.x, event?.y, true))
            }
        }
        invalidate() // onDraw를 호출함
        return true
    }

    fun reset() {
        list.clear()
        invalidate()
    }

    fun getSign() : ArrayList<Point> {
        return list;
    }

    fun setSign(sign: List<Point>) {
        list.addAll(sign)
        invalidate()
    }
}
