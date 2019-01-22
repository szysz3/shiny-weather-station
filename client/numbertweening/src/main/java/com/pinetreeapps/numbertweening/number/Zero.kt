package com.pinetreeapps.numbertweening.number

class Zero private constructor() : Number(Zero.points) {

    private object Holder {
        val instance = Zero()
    }

    companion object {
        val instance: Zero by lazy { Holder.instance }
        val points = arrayOf(floatArrayOf(0.24585635359116f, 0.552486187845304f),
                             floatArrayOf(0.24585635359116f, 0.331491712707182f),
                             floatArrayOf(0.370165745856354f, 0.0994475138121547f),
                             floatArrayOf(0.552486187845304f, 0.0994475138121547f),
                             floatArrayOf(0.734806629834254f, 0.0994475138121547f),
                             floatArrayOf(0.861878453038674f, 0.331491712707182f),
                             floatArrayOf(0.861878453038674f, 0.552486187845304f),
                             floatArrayOf(0.861878453038674f, 0.773480662983425f),
                             floatArrayOf(0.734806629834254f, 0.994475138121547f),
                             floatArrayOf(0.552486187845304f, 0.994475138121547f),
                             floatArrayOf(0.370165745856354f, 0.994475138121547f),
                             floatArrayOf(0.24585635359116f, 0.773480662983425f),
                             floatArrayOf(0.24585635359116f, 0.552486187845304f))

    }
}
