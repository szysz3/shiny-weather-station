package com.pinetreeapps.numbertweening.util

import com.pinetreeapps.numbertweening.number.*

object NumberUtils {

    fun getControlPointsFor(start: Int): Array<FloatArray> {
        return when (start) {
            -1   -> Null.points
            0    -> Zero.points
            1    -> One.points
            2    -> Two.points
            3    -> Three.points
            4    -> Four.points
            5    -> Five.points
            6    -> Six.points
            7    -> Seven.points
            8    -> Eight.points
            9    -> Nine.points
            else -> throw IndexOutOfBoundsException("Unsupported number requested")
        }
    }
}