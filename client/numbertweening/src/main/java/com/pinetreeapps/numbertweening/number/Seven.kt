package com.pinetreeapps.numbertweening.number

class Seven private constructor() : Number(Seven.points) {

    private object Holder {
        val instance = Seven()
    }

    companion object {
        val instance: Seven by lazy { Holder.instance }
        val points = arrayOf(floatArrayOf(0.259668508287293f, 0.116022099447514f),
                             floatArrayOf(0.259668508287293f, 0.116022099447514f),
                             floatArrayOf(0.87292817679558f, 0.116022099447514f),
                             floatArrayOf(0.87292817679558f, 0.116022099447514f),
                             floatArrayOf(0.87292817679558f, 0.116022099447514f),
                             floatArrayOf(0.7f, 0.422099447513812f),
                             floatArrayOf(0.7f, 0.422099447513812f),
                             floatArrayOf(0.7f, 0.422099447513812f),
                             floatArrayOf(0.477348066298343f, 0.733149171270718f),
                             floatArrayOf(0.477348066298343f, 0.733149171270718f),
                             floatArrayOf(0.477348066298343f, 0.733149171270718f),
                             floatArrayOf(0.25414364640884f, 1f),
                             floatArrayOf(0.25414364640884f, 1f))
    }
}
