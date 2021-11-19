package com.eratani.android.ui.jantung

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.eratani.android.R

class DetakJantungView : AppCompatImageView {
    private var heartDrawable: Drawable? = null
    var isHeartBeating = false
        private set
    private var scaleFactor = DEFAULT_SCALE_FACTOR
    var reductionScaleFactor = -scaleFactor
    var duration = DEFAULT_DURATION

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        populateFromAttributes(context, attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        populateFromAttributes(context, attrs)
        init()
    }

    private fun init() {
        //make this not mandatory
        heartDrawable = ContextCompat.getDrawable(context, R.drawable.ic_heart_red_24dp)
        setImageDrawable(heartDrawable)
    }

    private fun populateFromAttributes(context: Context, attrs: AttributeSet?) {
//        TypedArray a = context.getTheme().obtainStyledAttributes(
//                attrs,
//                R.styleable.HeartBeatView,
//                0, 0
//        );
//        try {
//            scaleFactor = a.getFloat(R.styleable.HeartBeatView_scaleFactor, DEFAULT_SCALE_FACTOR);
//            reductionScaleFactor = -scaleFactor;
//            duration = a.getInteger(R.styleable.HeartBeatView_duration, DEFAULT_DURATION);
//
//
//        } finally {
//            a.recycle();
//        }
    }

    fun toggle() {
        if (isHeartBeating) {
            stop()
        } else {
            start()
        }
    }

    fun start() {
        isHeartBeating = true
        animate().scaleXBy(scaleFactor).scaleYBy(scaleFactor).setDuration(duration.toLong())
            .setListener(scaleUpListener)
    }

    fun stop() {
        isHeartBeating = false
        clearAnimation()
    }

    fun setDurationBasedOnBPM(bpm: Int) {
        if (bpm > 0) {
            duration = Math.round(milliInMinute / bpm / 3f)
        }
    }

    fun getScaleFactor(): Float {
        return scaleFactor
    }

    fun setScaleFactor(scaleFactor: Float) {
        this.scaleFactor = scaleFactor
        reductionScaleFactor = -scaleFactor
    }

    private val scaleUpListener: Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            animate().scaleXBy(reductionScaleFactor).scaleYBy(reductionScaleFactor)
                .setDuration(duration.toLong()).setListener(scaleDownListener)
        }

        override fun onAnimationCancel(animation: Animator) {}
    }
    private val scaleDownListener: Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
        override fun onAnimationEnd(animation: Animator) {
            if (isHeartBeating) {
                //duration twice as long for the upscale
                animate().scaleXBy(scaleFactor).scaleYBy(scaleFactor)
                    .setDuration((duration * 2).toLong()).setListener(scaleUpListener)
            }
        }

        override fun onAnimationCancel(animation: Animator) {}
    }

    companion object {
        private const val TAG = "HeartBeatView"
        private const val DEFAULT_SCALE_FACTOR = 0.2f
        private const val DEFAULT_DURATION = 50
        private const val milliInMinute = 60000
    }
}