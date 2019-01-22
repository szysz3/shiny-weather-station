package com.pinetreeapps.numbertweening.number

class Two private constructor() : Number(Two.points) {

    private object Holder {
        val instance = Two()
    }

    companion object {
        val instance: Two by lazy { Holder.instance }
        val points = arrayOf(floatArrayOf(0.30939226519337f, 0.331491712707182f),
                             floatArrayOf(0.325966850828729f, 0.0110497237569061f),
                             floatArrayOf(0.790055248618785f, 0.0220994475138122f),
                             floatArrayOf(0.798342541436464f, 0.337016574585635f),
                             floatArrayOf(0.798342541436464f, 0.430939226519337f),
                             floatArrayOf(0.718232044198895f, 0.541436464088398f),
                             floatArrayOf(0.596685082872928f, 0.674033149171271f),
                             floatArrayOf(0.519337016574586f, 0.762430939226519f),
                             floatArrayOf(0.408839779005525f, 0.856353591160221f),
                             floatArrayOf(0.314917127071823f, 0.977900552486188f),
                             floatArrayOf(0.314917127071823f, 0.977900552486188f),
                             floatArrayOf(0.812154696132597f, 0.977900552486188f),
                             floatArrayOf(0.812154696132597f, 0.977900552486188f))
    }
}
