package com.pinetreeapps.numbertweening.number

class Eight private constructor() : Number(Eight.points) {

    private object Holder {
        val instance = Eight()
    }

    companion object {
        val instance: Eight by lazy { Holder.instance }
        val points = arrayOf(floatArrayOf(0.558011049723757f, 0.530386740331492f),
                             floatArrayOf(0.243093922651934f, 0.524861878453039f),
                             floatArrayOf(0.243093922651934f, 0.104972375690608f),
                             floatArrayOf(0.558011049723757f, 0.104972375690608f),
                             floatArrayOf(0.850828729281768f, 0.104972375690608f),
                             floatArrayOf(0.850828729281768f, 0.530386740331492f),
                             floatArrayOf(0.558011049723757f, 0.530386740331492f),
                             floatArrayOf(0.243093922651934f, 0.530386740331492f),
                             floatArrayOf(0.198895027624309f, 0.988950276243094f),
                             floatArrayOf(0.558011049723757f, 0.988950276243094f),
                             floatArrayOf(0.850828729281768f, 0.988950276243094f),
                             floatArrayOf(0.850828729281768f, 0.530386740331492f),
                             floatArrayOf(0.558011049723757f, 0.530386740331492f))
    }
}