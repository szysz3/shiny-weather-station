package com.pinetreeapps.numbertweening.number

class Null private constructor() : Number(Null.points) {

    private object Holder {
        val instance = Null()
    }

    companion object {
        val instance: Null by lazy { Holder.instance }
        val points = arrayOf(floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f),
                             floatArrayOf(0.5f, 0.5f))
    }
}