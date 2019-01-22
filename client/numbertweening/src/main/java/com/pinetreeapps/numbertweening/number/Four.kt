package com.pinetreeapps.numbertweening.number


class Four private constructor() : Number(Four.points) {

    private object Holder {
        val instance = Four()
    }

    companion object {
        val instance: Four by lazy { Holder.instance }
        val points = arrayOf(floatArrayOf(0.856353591160221f, 0.806629834254144f),
                             floatArrayOf(0.856353591160221f, 0.806629834254144f),
                             floatArrayOf(0.237569060773481f, 0.806629834254144f),
                             floatArrayOf(0.237569060773481f, 0.806629834254144f),
                             floatArrayOf(0.237569060773481f, 0.806629834254144f),
                             floatArrayOf(0.712707182320442f, 0.138121546961326f),
                             floatArrayOf(0.712707182320442f, 0.138121546961326f),
                             floatArrayOf(0.712707182320442f, 0.138121546961326f),
                             floatArrayOf(0.712707182320442f, 0.806629834254144f),
                             floatArrayOf(0.712707182320442f, 0.806629834254144f),
                             floatArrayOf(0.712707182320442f, 0.806629834254144f),
                             floatArrayOf(0.712707182320442f, 0.988950276243094f),
                             floatArrayOf(0.712707182320442f, 0.988950276243094f))

    }
}