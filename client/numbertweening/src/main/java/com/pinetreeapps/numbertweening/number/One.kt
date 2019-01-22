package com.pinetreeapps.numbertweening.number

class One private constructor() : Number(One.points) {

    private object Holder {
        val instance = One()
    }

    companion object {
        val instance: One by lazy { Holder.instance }
        val points = arrayOf(floatArrayOf(0.425414364640884f, 0.113259668508287f),
                             floatArrayOf(0.425414364640884f, 0.113259668508287f),
                             floatArrayOf(0.577348066298343f, 0.113259668508287f),
                             floatArrayOf(0.577348066298343f, 0.113259668508287f),
                             floatArrayOf(0.577348066298343f, 0.113259668508287f),
                             floatArrayOf(0.577348066298343f, 1f),
                             floatArrayOf(0.577348066298343f, 1f),
                             floatArrayOf(0.577348066298343f, 1f),
                             floatArrayOf(0.577348066298343f, 1f),
                             floatArrayOf(0.577348066298343f, 1f),
                             floatArrayOf(0.577348066298343f, 1f),
                             floatArrayOf(0.577348066298343f, 1f),
                             floatArrayOf(0.577348066298343f, 1f))
    }
}