package com.pinetreeapps.numbertweening.util

import android.animation.TypeEvaluator

class NumberEvaluator : TypeEvaluator<Array<FloatArray>> {
    override fun evaluate(fraction: Float, startValue: Array<FloatArray>,
                          endValue: Array<FloatArray>): Array<FloatArray> {

        val pointsCount = startValue.size
        val cachedPoints = Array(pointsCount) { FloatArray(2) }
        for (i in 0 until pointsCount) {
            cachedPoints[i][0] = startValue[i][0] + fraction * (endValue[i][0] - startValue[i][0])
            cachedPoints[i][1] = startValue[i][1] + fraction * (endValue[i][1] - startValue[i][1])
        }

        return cachedPoints
    }
}
