package hhh.qaq.ovo.ui.player

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.imageview.ShapeableImageView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModelFragment
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.utils.BitmapUtil
import hhh.qaq.ovo.utils.initRotationAnimation
import hhh.qaq.ovo.utils.log
import hhh.qaq.ovo.utils.screenHeight
import hhh.qaq.ovo.viewmodel.PlayerCoverViewModel

/**
 * @By Journey 2020/12/31
 * @Description
 */
class PlayerCoverFragment:BaseViewModelFragment<PlayerCoverViewModel>(R.layout.fragment_player_cover,PlayerCoverViewModel::class.java) {
    private var mCover:ImageView?=null
    private var mBitmap:Bitmap?=null
    private val isMusicPlaying = PlayManager.isPlaying()
    private var mRotationAnimator: ObjectAnimator? = null
    override fun initView() {
        super.initView()
        if (mCover==null) {
            mCover = mRootView?.findViewById(R.id.cover2View)
        }
        initAnimator()
        if (isMusicPlaying) {
            resumeCoverRotation()
        } else {
            stopCoverRotation()
        }
        if (PlayManager.getPlayingMusic()!=null) {
            startCoverRotation()
            setCoverBitmapDrawable()
        }
    }

    private fun setCoverBitmapDrawable() {
        if (mBitmap==null){
            mBitmap = BitmapFactory.decodeResource(resources,R.drawable.disk)
        }
        mCover?.setImageDrawable(BitmapUtil.getCoverDrawable(mBitmap))
//        mCover.setImageDrawable(BitmapUtil.getCoverDrawable2(mBitmap))
//        val lp = mCover.layoutParams as ConstraintLayout.LayoutParams
//        val marginTop = (Constant.SCALE_DISC_MARGIN_TOP* screenHeight).toInt()
//        lp.setMargins(0,marginTop,0,0)
//        mCover.layoutParams = lp
    }

    private fun initAnimator() {
        if (mRotationAnimator == null) {
            mRotationAnimator = initRotationAnimation(mCover!!)
        }
    }

    fun startCoverRotation() {
        if (isMusicPlaying) {
            mRotationAnimator?.pause()
            mRotationAnimator?.start()
        }
    }

    fun stopCoverRotation() {
        mRotationAnimator?.pause()
    }

    fun resumeCoverRotation() {
        if (mRotationAnimator?.isPaused == true && isMusicPlaying) {
            mRotationAnimator?.resume()
        }
    }

    fun setCoverBitmap(bitmap: Bitmap?) {
        mBitmap = bitmap
        setCoverBitmapDrawable()
    }

    override fun onResume() {
        super.onResume()
        if (mRotationAnimator != null && mRotationAnimator?.isPaused!! && isMusicPlaying) {
            mRotationAnimator?.resume()
        }
    }

    override fun onStop() {
        super.onStop()
        mRotationAnimator?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mRotationAnimator != null) {
            mRotationAnimator?.cancel()
            mRotationAnimator = null
        }
    }
}