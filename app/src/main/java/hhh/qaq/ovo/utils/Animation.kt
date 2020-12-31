package hhh.qaq.ovo.utils

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.core.animation.addListener
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart

/**
 * @By Journey 2020/12/31
 * @Description
 */

fun View.setTranslateAnimation() {
    this.animation =
        TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_SELF,
            1.0f, Animation.RELATIVE_TO_SELF, 0.0f).apply {
            duration = 300
        }
}

fun initRotationAnimation(iv: ImageView):ObjectAnimator {
    return ObjectAnimator.ofFloat(iv,"rotation", 0F, 359F).apply {
        duration = (20 * 1000).toLong()
        repeatCount = -1
        repeatMode = ObjectAnimator.RESTART
        interpolator = LinearInterpolator()
        addUpdateListener {
            iv.rotation = it.animatedValue as Float
        }
    }
}

fun initScaleAnimation(iv:ImageView):ObjectAnimator{
    return ObjectAnimator.ofFloat(iv, "scaleX", 1f, 0.7f).apply {
        duration = 500L
        interpolator = AccelerateInterpolator()
        interpolator = AccelerateInterpolator()
//        addUpdateListener {
//            iv.scaleY = it.animatedValue as Float
//            iv.scaleX = it.animatedValue as Float
//        }
        addListener {
            doOnStart {
                iv.alpha = 0f
            }
        }
    }
}

fun initAlphaAnimation(iv:ImageView):ObjectAnimator{
    return ObjectAnimator.ofFloat(iv, "alpha", 0f, 1F).apply {
        duration = 300
        addUpdateListener {
            iv.alpha = it.animatedValue as Float
        }
        addListener {
            doOnEnd {
                iv.alpha = 1f
            }
            doOnCancel {
                iv.alpha = 1f
            }
        }
    }
}