package com.ssafy.ssaign.src.main.sign

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ssafy.ssaign.config.ApplicationClass.Companion.dpHeight
import com.ssafy.ssaign.src.main.sign.model.Point

class DrawSign(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var isValid = true
    var list = arrayListOf<Point>()
    var paint: Paint = Paint()

    init {
        paint.strokeWidth = 14F
        paint.color = Color.BLACK

        // 곡선 처리를 위해 추가한 코드들
        paint.isAntiAlias = true; // enable anti aliasing
        paint.isDither = true; // enable dithering
        paint.style = Paint.Style.STROKE; // set to STOKE
        paint.strokeJoin = Paint.Join.ROUND; // set the join to round you want
        paint.strokeCap = Paint.Cap.ROUND;  // set the paint cap to round too
        paint.pathEffect = CornerPathEffect(14F); // set the path effect when they join.
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
        if (isValid) {
            when (event?.action) {
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
        }
        return true
    }

    fun reset() {
        list.clear()
        invalidate()
    }

    fun getSign() : ArrayList<Point> {
        return list;
    }

    fun setSign(sign: List<Point>, caller: String) {
        val rate = if(caller == "SignConfirmFragment") {
            paint.strokeWidth = 20 / (dpHeight / 68)
            dpHeight / 68
        } else {
            paint.strokeWidth = 30 / (dpHeight / 200)
            dpHeight / 200
        }

        val mapped = sign.map { it -> Point(it.x / rate, it.y / rate, it.isContinue) }
        list.addAll(mapped)
        invalidate()
    }
}
