package hhh.qaq.ovo.ui.player

import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModelFragment
import hhh.qaq.ovo.databinding.FragmentLyricBinding
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.viewmodel.PlayerLyricViewModel

/**
 * @By Journey 2020/12/31
 * @Description
 */
class PlayerLyricFragment:BaseViewModelFragment<PlayerLyricViewModel>(R.layout.fragment_lyric,PlayerLyricViewModel::class.java) {
    private var mIsInit:Boolean = false
    private lateinit var mPlayerLyricBinding:FragmentLyricBinding
    override fun initView() {
        super.initView()
        mPlayerLyricBinding = mBinding as FragmentLyricBinding
        mPlayerLyricBinding.lrcView.setDraggable(true){ _, time ->
            PlayManager.seekTo(time.toInt())
            if (!PlayManager.isPlaying()) {
                PlayManager.playPause()
            }
            true
        }
        mIsInit = true
    }

    fun updateLyricProgress(progress:Long) {
        if (mIsInit) {
            if (mPlayerLyricBinding.lrcView.hasLrc()) {
                mViewModel.updateLyricProgress(progress)
            }
        }
    }
}