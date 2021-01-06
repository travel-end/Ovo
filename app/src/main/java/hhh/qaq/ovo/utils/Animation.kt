package hhh.qaq.ovo.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
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
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.data.db.SongOperator
import hhh.qaq.ovo.event.GlobalEventBus
import hhh.qaq.ovo.event.PlaylistEvent
import hhh.qaq.ovo.model.Music

/**
 * @By Journey 2020/12/31
 * @Description
 */

// 底部上移动画
fun View.setTranslateAnimation() {
    this.animation =
        TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_SELF,
            1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        ).apply {
            duration = 300
        }
}

// 旋转动画
fun initRotationAnimation(iv: ImageView): ObjectAnimator {
    return ObjectAnimator.ofFloat(iv, "rotation", 0F, 359F).apply {
        duration = (20 * 1000).toLong()
        repeatCount = -1
        repeatMode = ObjectAnimator.RESTART
        interpolator = LinearInterpolator()
        addUpdateListener {
            iv.rotation = it.animatedValue as Float
        }
    }
}

// 缩放动画
fun initScaleAnimation(iv: ImageView): ObjectAnimator {
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

// 缩放动画（收藏歌曲-->爱心演示动画）短暂显示的动画 不返回对象  有内存泄露的危险很低
fun startCollectMusicAnimation(iv: ImageView){
    ValueAnimator.ofFloat(1f, 1.3f, 0.8f, 1f).apply {
        duration = 600
        addUpdateListener {
            iv.scaleY = it.animatedValue as Float
            iv.scaleX = it.animatedValue as Float
        }
//        addListener(object :Animator.AnimatorListener{
//            override fun onAnimationRepeat(animation: Animator?) {
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//                music?.let {
//                    "isLoving2:${SongOperator.updateCollectMusic(it)}".log()
//                    it.isLove = SongOperator.updateCollectMusic(it)
//                    GlobalEventBus.playListChanged.value = PlaylistEvent(Constant.PLAYLIST_LOVE_ID)
//                }
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//            }
//
//            override fun onAnimationStart(animation: Animator?) {
//            }
//
//        })
        // 我淦  没用！！
//        addListener {
//            doOnEnd {
//                "doOnEnd".log()
//                music?.let {
//                    "isLoving2:${SongOperator.updateCollectMusic(it)}".log()
//                    it.isLove = SongOperator.updateCollectMusic(it)
//                    GlobalEventBus.playListChanged.value = PlaylistEvent(Constant.PLAYLIST_LOVE_ID)
//                }
//            }
//        }
    }.start()
}


// 透明度动画
fun initAlphaAnimation(iv: ImageView): ObjectAnimator {
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