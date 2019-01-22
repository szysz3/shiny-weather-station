package com.pinetreeapps.numbertweening.number

abstract class Number(controlPoints: Array<FloatArray>) {
    companion object {
        const val NO_VALUE = -1
    }

    private val controlPointsLength: Int = (controlPoints.count() + 2) / 3

}