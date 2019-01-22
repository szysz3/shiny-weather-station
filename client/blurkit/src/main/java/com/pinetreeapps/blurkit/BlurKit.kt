package com.pinetreeapps.blurkit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View

class BlurKit private constructor() {
    private lateinit var renderScript: RenderScript

    private object Holder {
        val instance = BlurKit()
    }

    companion object {
        val instance: BlurKit by lazy { Holder.instance }
        const val downScaleFactor = 1f
    }

    fun init(context: Context) {
        renderScript = RenderScript.create(context)
    }

    fun blur(src: Bitmap, radius: Int): Bitmap {
        val input = Allocation.createFromBitmap(renderScript, src)
        val output = Allocation.createTyped(renderScript, input.type)
        val script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        script.setRadius(radius.toFloat())
        script.setInput(input)
        script.forEach(output)
        output.copyTo(src)

        return src
    }

    fun blur(src: View, radius: Int): Bitmap {
        val bitmap = getBitmapForView(src, downScaleFactor)

        return blur(bitmap, radius)
    }

    fun fastBlur(src: View, radius: Int, downscaleFactor: Float): Bitmap {
        val bitmap = getBitmapForView(src, downscaleFactor)

        return blur(bitmap, radius)
    }

    private fun getBitmapForView(src: View, downscaleFactor: Float): Bitmap {
        val bitmap = Bitmap.createBitmap((src.width * downscaleFactor).toInt(),
                                         (src.height * downscaleFactor).toInt(),
                                         Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        val matrix = Matrix()
        matrix.preScale(downscaleFactor, downscaleFactor)
        canvas.matrix = matrix
        src.draw(canvas)

        return bitmap
    }
}
