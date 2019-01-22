package com.pinetreeapps.blurkit

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import java.lang.ref.WeakReference

class BlurLayout : FrameLayout {
    companion object {
        const val DOWNSCALE_FACTOR = 0.12f
        const val BLUR_RADIUS = 12
        const val FPS: Long = 60
    }

    private val TAG = BlurLayout::class.java.simpleName

    private var running = false
    private var attachedToWindow = false
    private var positionLocked = false
    private var viewLocked = false
    private var imageView: ImageView
    private var activityView: WeakReference<View>

    private val invalidationLoop = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            invalidate()
            Choreographer.getInstance().postFrameCallbackDelayed(this, (1000 / FPS))
        }
    }

    private lateinit var lockedBitmap: Bitmap
    private lateinit var lockedPoint: Point

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
                                                                                   attrs,
                                                                                   defStyleAttr) {
        BlurKit.instance.init(context)

        activityView = WeakReference<View>(getActivityView())
        imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY

        addView(imageView)
    }

    fun startBlur() {
        if (running) return

        Choreographer.getInstance().postFrameCallback(invalidationLoop)
        running = true
    }

    fun pauseBlur() {
        if (!running) return

        Choreographer.getInstance().removeFrameCallback(invalidationLoop)
        running = false
    }

    private fun lockView() {
        viewLocked = true
        activityView.get().let {
            val view = it?.rootView ?: return
            try {
                alpha = 0f
                lockedBitmap = getDownscaledBitmapForView(view,
                                                          Rect(0, 0, view.width, view.height),
                                                          DOWNSCALE_FACTOR)
                alpha = 1f
                lockedBitmap = BlurKit.instance.blur(lockedBitmap, BLUR_RADIUS)
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attachedToWindow = true
        startBlur()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        attachedToWindow = false
        pauseBlur()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    override fun invalidate() {
        super.invalidate()
        val bitmap = blur()
        bitmap.let { imageView.setImageBitmap(it) }
    }

    private fun blur(): Bitmap? {
        if (isInEditMode) return null
        if (viewToBlur == null) return null

        // Check the reference to the parent view.
        // If not available, attempt to make it.

        if (activityView.get() == null) {
            activityView = WeakReference<View>(getActivityView())
            if (activityView.get() == null) {
                return null
            }
        }

        val pointRelativeToActivityView: Point
        if (positionLocked) {
            // Generate a locked point if null.
            if (lockedPoint == null) {
                lockedPoint = getPositionInScreen()
            }

            // Use locked point.
            pointRelativeToActivityView = lockedPoint
        } else {
            // Calculate the relative point to the parent view.
            pointRelativeToActivityView = getPositionInScreen()
        }

        // Set alpha to 0 before creating the parent view bitmap.
        // The blur view shouldn't be visible in the created bitmap.
        alpha = 0f

        // Screen sizes for bound checks
        val screenWidth = activityView.get()!!.width
        val screenHeight = activityView.get()!!.height

        // The final dimensions of the blurred bitmap.
        val width = (width * DOWNSCALE_FACTOR).toInt()
        val height = (height * DOWNSCALE_FACTOR).toInt()

        // The X/Y position of where to crop the bitmap.
        val x = (pointRelativeToActivityView.x * DOWNSCALE_FACTOR).toInt()
        val y = (pointRelativeToActivityView.y * DOWNSCALE_FACTOR).toInt()

        // Padding to add to crop pre-blur.
        // Blurring straight to edges has side-effects so padding is added.
        val xPadding = getWidth() / 8
        val yPadding = getHeight() / 8

        // Calculate padding independently for each side, checking edges.
        var leftOffset = -xPadding
        leftOffset = if (x + leftOffset >= 0) leftOffset else 0

        var rightOffset = xPadding
        rightOffset =
                if (x + getWidth() + rightOffset <= screenWidth) rightOffset else screenWidth - getWidth() - x

        var topOffset = -yPadding
        topOffset = if (y + topOffset >= 0) topOffset else 0

        var bottomOffset = yPadding
        bottomOffset = if (y + height + bottomOffset <= screenHeight) bottomOffset else 0

        // Parent view bitmap, downscaled with mDownscaleFactor
        var bitmap: Bitmap
        if (viewLocked) {
            // It's possible for mLockedBitmap to be null here even with view locked.
            // lockView() should always properly set mLockedBitmap if this code is reached
            // (it passed previous checks), so recall lockView and assume it's good.
            if (lockedBitmap == null) {
                lockView()
            }

            if (width == 0 || height == 0) {
                return null
            }

            bitmap = Bitmap.createBitmap(lockedBitmap, x, y, width, height)
        } else {
            try {
                // Create parent view bitmap, cropped to the BlurLayout area with above padding.
                //pass sth else
                bitmap = getDownscaledBitmapForView(viewToBlur,/*(activityView.get(),*/
                                                    Rect(pointRelativeToActivityView.x + leftOffset,
                                                         pointRelativeToActivityView.y + topOffset,
                                                         pointRelativeToActivityView.x + getWidth() + Math.abs(
                                                                 leftOffset) + rightOffset,
                                                         pointRelativeToActivityView.y + getHeight() + Math.abs(
                                                                 topOffset) + bottomOffset),
                                                    DOWNSCALE_FACTOR)
            } catch (e: NullPointerException) {
                return null
            }

        }

        if (!viewLocked) {
            // Blur the bitmap.
            bitmap = BlurKit.instance.blur(bitmap, BLUR_RADIUS)

            //Crop the bitmap again to remove the padding.
            bitmap = Bitmap.createBitmap(bitmap,
                                         (Math.abs(leftOffset) * DOWNSCALE_FACTOR).toInt(),
                                         (Math.abs(topOffset) * DOWNSCALE_FACTOR).toInt(),
                                         width,
                                         height)

        }

        // Make self visible again.
        alpha = 1f

        // Set background as blurred bitmap.
        return bitmap
    }

    private var viewToBlur: View? = null
    fun setViewToBlur(toBlur: View) {
        viewToBlur = toBlur
    }

    private fun getActivityView(): View? {
        val activity: Activity
        try {
            activity = context as Activity
        } catch (e: ClassCastException) {
            return null
        }

        return activity.window.decorView.findViewById(android.R.id.content)
    }

    private fun getPositionInScreen(): Point {
        val pointF = getPositionInScreen(this)
        return Point(pointF.x.toInt(), pointF.y.toInt())
    }

    private fun getPositionInScreen(view: View): PointF {
        if (parent == null) {
            return PointF()
        }

        val parent: ViewGroup?
        try {
            parent = view.parent as ViewGroup
        } catch (e: Exception) {
            return PointF()
        }

        if (parent == null) {
            return PointF()
        }

        val point = getPositionInScreen(parent)
        point.offset(view.x, view.y)
        return point
    }

    private fun getDownscaledBitmapForView(view: View?, crop: Rect,
                                           downscaleFactor: Float): Bitmap {
        val screenView = view!!//view!!.rootView

        val width = (crop.width() * downscaleFactor).toInt()
        val height = (crop.height() * downscaleFactor).toInt()

        if (screenView.width <= 0 || screenView.height <= 0 || width <= 0 || height <= 0) {
            throw IllegalStateException("No screen available (width or height = 0)")
        }

        val dx = -crop.left * downscaleFactor
        val dy = -crop.top * downscaleFactor

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val matrix = Matrix()
        matrix.preScale(downscaleFactor, downscaleFactor)
        matrix.postTranslate(dx, dy)
        canvas.matrix = matrix
        screenView.draw(canvas)

        return bitmap
    }
}