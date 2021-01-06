package hhh.qaq.ovo.ui.player

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.os.Bundle
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModelFragment
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.databinding.FragmentPlayerCoverBinding
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.utils.initRotationAnimation
import hhh.qaq.ovo.viewmodel.PlayerCoverViewModel

/**
 * @By Journey 2020/12/31
 * @Description
 */
class PlayerCoverFragment:BaseViewModelFragment<PlayerCoverViewModel>(R.layout.fragment_player_cover,PlayerCoverViewModel::class.java) {
    private lateinit var mPlayerCoverBinding:FragmentPlayerCoverBinding
    private var mRotationAnimator: ObjectAnimator? = null
    override fun initView() {
        super.initView()
        mPlayerCoverBinding = mBinding as FragmentPlayerCoverBinding
        initAnimator()
        if (PlayManager.isPlaying()) {
            resumeCoverRotation()
        } else {
            stopCoverRotation()
        }
        if (PlayManager.getPlayingMusic()!=null) {
            startCoverRotation()
        }
    }

    private fun initAnimator() {
        if (mRotationAnimator == null) {
            mRotationAnimator = initRotationAnimation(mPlayerCoverBinding.cover2View)
        }
    }

    fun startCoverRotation() {
        if (PlayManager.isPlaying()) {
            mRotationAnimator?.pause()
            mRotationAnimator?.start()
        }
    }

    fun stopCoverRotation() {
        mRotationAnimator?.pause()
    }

    fun resumeCoverRotation() {
        if (mRotationAnimator?.isPaused == true && PlayManager.isPlaying()) {
            mRotationAnimator?.resume()
        }
    }

    fun setCoverBitmap(bitmap: Bitmap?) {
        arguments= Bundle().apply {
            putParcelable(Constant.KEY_COVER_BITMAP,bitmap)
        }
    }

    override fun onResume() {
        super.onResume()
        if (mRotationAnimator != null && mRotationAnimator?.isPaused!! && PlayManager.isPlaying()) {
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