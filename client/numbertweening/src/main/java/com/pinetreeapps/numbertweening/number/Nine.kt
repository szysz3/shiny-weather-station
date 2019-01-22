package com.pinetreeapps.numbertweening.number

class Nine private constructor() : Number(Nine.points) {

    private object Holder {
        val instance = Nine()
    }

    companion object {
        val instance: Nine by lazy { Holder.instance }
        val points = arrayOf(floatArrayOf(0.80939226519337f, 0.552486187845304f),
                             floatArrayOf(0.685082872928177f, 0.751381215469613f),
                             floatArrayOf(0.298342541436464f, 0.740331491712707f),
                             floatArrayOf(0.259668508287293f, 0.408839779005525f),
                             floatArrayOf(0.232044198895028f, 0.0441988950276243f),
                             floatArrayOf(0.81767955801105f, -0.0441988950276243f),
                             floatArrayOf(0.850828729281768f, 0.408839779005525f),
                             floatArrayOf(0.839779005524862f, 0.596685082872928f),
                             floatArrayOf(0.712707182320442f, 0.668508287292818f),
                             floatArrayOf(0.497237569060773f, 0.994475138121547f),
                             floatArrayOf(0.497237569060773f, 0.994475138121547f),
                             floatArrayOf(0.497237569060773f, 0.994475138121547f),
                             floatArrayOf(0.497237569060773f, 0.994475138121547f))
    }
}