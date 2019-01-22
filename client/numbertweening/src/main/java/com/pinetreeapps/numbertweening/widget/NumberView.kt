package com.pinetreeapps.numbertweening.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Property
import android.view.View
import com.pinetreeapps.numbertweening.util.NumberEvaluator
import com.pinetreeapps.numbertweening.util.NumberUtils

class NumberView : View {
    private var mPaint = Paint()
    private var mPath = Path()

    var strokeWidth: Float = 20f
        set(value) {
            mPaint.strokeWidth = value
        }

    var controlPoints: Array<FloatArray>? = null
        set(controlPoints) {
            field = controlPoints
            invalidate()
        }

    var startNumber = -1
    var endNumber = -1

    private val numberEvaluator = NumberEvaluator()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
                                                                                  attrs,
                                                                                  defStyleAttr) {
        init()
    }

    fun animate(start: Int, end: Int): ObjectAnimator {
        startNumber = start
        endNumber = end

        val startPoints = NumberUtils.getControlPointsFor(start)
        val endPoints = NumberUtils.getControlPointsFor(end)

        return ObjectAnimator.ofObject(this,
                                       CONTROL_POINTS_PROPERTY,
                                       numberEvaluator,
                                       startPoints,
                                       endPoints)
    }

    fun animate(end: Int): ObjectAnimator {
        startNumber = -1
        endNumber = end

        val startPoints = NumberUtils.getControlPointsFor(-1)
        val endPoints = NumberUtils.getControlPointsFor(end)

        return ObjectAnimator.ofObject(this,
                                       CONTROL_POINTS_PROPERTY,
                                       numberEvaluator,
                                       startPoints,
                                       endPoints)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (this.controlPoints == null) return

        val length = this.controlPoints!!.size

        val height = measuredHeight
        val width = measuredWidth

        val minDimen = (if (height > width) width else height).toFloat()

        mPath.reset()
        mPath.moveTo(minDimen * this.controlPoints!![0][0], minDimen * this.controlPoints!![0][1])
        var i = 1
        while (i < length) {
            mPath.cubicTo(minDimen * this.controlPoints!![i][0],
                          minDimen * this.controlPoints!![i][1],
                          minDimen * this.controlPoints!![i + 1][0],
                          minDimen * this.controlPoints!![i + 1][1],
                          minDimen * this.controlPoints!![i + 2][0],
                          minDimen * this.controlPoints!![i + 2][1])
            i += 3
        }
        canvas.drawPath(mPath, mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = measuredWidth
        var height = measuredHeight
        val widthWithoutPadding = width - paddingLeft - paddingRight
        val heigthWithoutPadding = height - paddingTop - paddingBottom

        val maxWidth = (heigthWithoutPadding * RATIO).toInt()
        val maxHeight = (widthWithoutPadding / RATIO).toInt()

        if (widthWithoutPadding > maxWidth) {
            width = maxWidth + paddingLeft + paddingRight
        } else {
            height = maxHeight + paddingTop + paddingBottom
        }

        setMeasuredDimension(width, height)
    }

    private fun init() {
        // A new paint with the style as stroke.
        mPaint.isAntiAlias = true
        mPaint.color = Color.WHITE
        mPaint.strokeWidth = strokeWidth
        mPaint.style = Paint.Style.STROKE
    }

    companion object {
        private const val RATIO = 0.95f
        private val CONTROL_POINTS_PROPERTY = object :
                Property<NumberView, Array<FloatArray>>(Array<FloatArray>::class.java,
                                                        "controlPoints") {
            override operator fun get(`object`: NumberView): Array<FloatArray>? {
                return `object`.controlPoints
            }

            override operator fun set(`object`: NumberView, value: Array<FloatArray>) {
                `object`.controlPoints = value
            }
        }
    }
}