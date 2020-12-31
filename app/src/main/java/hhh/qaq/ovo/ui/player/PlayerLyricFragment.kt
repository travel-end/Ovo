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
    private lateinit var mLyricView:LrcView
    override fun initView() {
        super.initView()
        mLyricView = mRootView.findViewById(R.id.lrc_view)
        mLyricView.setDraggable(true){ _, time ->
            PlayManager.seekTo(time.toInt())
            if (!isPlaying) {
                PlayManager.playPause()
                // todo updatePlayProgress

            }
            true
        }
    }
}