package hhh.qaq.ovo.ui.player

import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModelFragment
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.viewmodel.PlayerLyricViewModel
import hhh.qaq.ovo.widget.lrc.LrcView

/**
 * @By Journey 2020/12/31
 * @Description
 */
class PlayerLyricFragment:BaseViewModelFragment<PlayerLyricViewModel>(R.layout.fragment_lyric,PlayerLyricViewModel::class.java) {
    private val isPlaying = PlayManager.isPlaying()
    private var mIsInit:Boolean = false
    private var mLyricView:LrcView?=null
    override fun initView() {
        super.initView()
        mLyricView = mRootView?.findViewById(R.id.lrc_view)
        mLyricView?.setDraggable(true){ _, time ->
            PlayManager.seekTo(time.toInt())
            if (!isPlaying) {
                PlayManager.playPause()
            }
            true
        }
        mIsInit = true
    }


    fun updateLyricProgress(progress:Long) {
        if (mIsInit) {
            if (mLyricView?.hasLrc()==true) {
                mViewModel.updateLyricProgress(progress)
            }
        }
    }
}