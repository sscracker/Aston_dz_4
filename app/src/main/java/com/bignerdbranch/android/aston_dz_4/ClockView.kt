package com.bignerdbranch.android.aston_dz_4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin



class ClockView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var padding = 0
    private var fontSize = 0
    private var numeralSpacing = 0
    private var handTruncation = 0
    private var hourHandTruncation = 0
    private var radius = 0
    private lateinit var paint: Paint
    private var rect: Rect = Rect()
    private  var isInit: Boolean = false
    private val numbers = listOf(1,2,3,4,5,6,7,8,9,10,11,12)


    private fun initClock() {
            height
            width
            padding = numeralSpacing + 50
            fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13F, resources.displayMetrics).toInt()
            val min = min(height,width)
            radius = min / 2 - padding
            handTruncation = min / 20
            hourHandTruncation = min / 7
            paint = Paint()
            isInit = true

        }

    override fun onDraw(canvas: Canvas?) {
        if (!isInit){
            initClock()
        }

        canvas?.drawColor(Color.BLACK)
        drawCircle(canvas)
        drawCenter(canvas)
        drawNumbers(canvas)
        drawHands(canvas)

        postInvalidateDelayed(500)
        invalidate()


    }


    private fun drawHand(canvas: Canvas?, loc: Int, isHour: Boolean) {
        val angle: Double = PI * loc / 30 - PI / 2
        val handRadius: Int = if (isHour){
            radius - handTruncation - hourHandTruncation
        } else{
            radius - handTruncation
        }

        canvas?.drawLine(
            (width / 2).toFloat(), (height / 2).toFloat(),
            (width/2 + cos(angle) * handRadius).toFloat(),
            (height /2 + sin(angle)* handRadius).toFloat(),
            paint)


    }


    private fun drawHands(canvas: Canvas?) {
        val c: Calendar = Calendar.getInstance()
        var hour = c.get(Calendar.HOUR_OF_DAY)
        drawHand(canvas,(hour + c.get(Calendar.MINUTE) / 60) * 5, true)
        drawHand(canvas, c.get(Calendar.MINUTE),false)
        drawHand(canvas, c.get(Calendar.SECOND),false)
    }

    private fun drawNumbers(canvas: Canvas?) {
        paint.textSize = fontSize.toFloat()
        for (number in numbers){
            val tmp: String = number.toString()
            paint.getTextBounds(tmp,0,tmp.length, rect)
            val angle: Double = PI / 6 * (number - 3)
            val x =  ((width / 2 + cos(angle) * radius - rect.width() / 2).toFloat())
            val y  = ((height / 2 + sin(angle) * radius + rect.height() / 2).toFloat())
            canvas?.drawText(tmp, x, y, paint)
        }
    }

    private fun drawCenter(canvas: Canvas?) {
        paint.style = Paint.Style.FILL
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 12F, paint)

    }

    private fun drawCircle(canvas: Canvas?) {
        paint.reset()
        paint.color = ContextCompat.getColor(context,R.color.white)
        paint.strokeWidth = 5F
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (radius + padding - 10).toFloat(), paint)

    }
}
